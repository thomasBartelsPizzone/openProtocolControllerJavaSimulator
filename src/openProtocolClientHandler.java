import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class openProtocolClientHandler extends Thread {
    openProtocolClientParserMIDClass oPCP = new openProtocolClientParserMIDClass();
    openProtocolClientSerializerMIDClass oPCS = new openProtocolClientSerializerMIDClass();
    public ServerSocket server;
    public Socket clientSocket;
    public int port;
    public DataOutputStream os;
    public InputStreamReader input;
    public JTextArea dialogBox;

//    //If multi-connection option is required.
    //
    public openProtocolClientHandler(ServerSocket servSocket, Socket socket, int portId, JTextArea txtArea) {
        this.clientSocket = socket;
        this.port = portId;
        this.server = servSocket;
        this.dialogBox = txtArea;
    }
    //
    void open() {
        String controllerMid;
        String errorCode = "01";
        String replyMessage = null;
        String inputLine;
        List<Object> inputCommandList = new ArrayList<>();
        //
        try {
            input = new InputStreamReader(clientSocket.getInputStream());
            os = new DataOutputStream(clientSocket.getOutputStream());
            //
            boolean keepOpen = true;
            while (keepOpen) {
                inputLine = readBytesFully(input);
                if (inputLine != null && !"null".equals(inputLine) && !"".equals(inputLine)) {
                    String integratorMid = oPCP.getMID(inputLine);
                    appendDialog(dialogBox, "host message serialized: \n" + inputLine);
                    controllerLogger.controllerLog("controller2Host", "host message serialized: " + inputLine, "info");
                    //
                    Object hostResponseParsed = oPCP.parseMessage(inputLine);
                    appendDialog(dialogBox, "controller message parsed: \n" + hostResponseParsed);
                    controllerLogger.controllerLog("controller2Host", "host message parsed: " + hostResponseParsed, "info");
                    //
                    //get the reply message
                    String replyMID = oPCP.getReply(inputLine);
                    label:
                    switch (Objects.requireNonNull(replyMID)) {
                        case "MID0005":
                            inputCommandList.add(integratorMid);
                            replyMessage = oPCS.midCmdSerializeString(replyMID, inputCommandList);
                            //
                            break;
                        case "MID0004":
                            inputCommandList.add(integratorMid);
                            inputCommandList.add(errorCode);
                            replyMessage = oPCS.midCmdSerializeString(replyMID, inputCommandList);
                        case "MID0003":
                            //close client socket
                            keepOpen = false;
                            break;
                        case "MID9999":
                            replyMessage = inputLine;
                            break;
                    }
                    controllerLogger.controllerLog("controller2Host", "controller auto reply: " + replyMessage, "info");
                    // update dialog box with message.
                    appendDialog(dialogBox, "reply message:\n" + replyMessage + "\n");
                    sendMessage(replyMessage);
                }
            }
            os.close();
        } catch (IOException e){ //| InstantiationException | IllegalAccessException | InvocationTargetException e) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
            controllerLogger.controllerLog("controllerRun", "Something went wrong in run\n" + e.getMessage() + "\n @ " + jsonTimestamp, "error");
        }
    }
    //
    void appendDialog(JTextArea tArea, String dialog){
        tArea.append(dialog);
        tArea.append("\n");
    }
    //
    String readBytesFully(InputStreamReader in) throws IOException {
        String returnString;
        int n = 0;
        int maxStr = 9999;
        int[] bS = new int[maxStr];
        while (n < maxStr) {
            int b = in.read();
            if (b == 0) {
                break;
            } else if (b != -1) {
                bS[n] = b;
            } else {
                break;
            }
            n += 1;
        }
        returnString = new String(bS, 0, n);
        return returnString;
    }
    //
    byte[] msgBytes(String serializedMsg) {
        return serializedMsg.getBytes(StandardCharsets.US_ASCII);
    }
    //
    void sendMessage(String msg) {
        try {
            os.write(msgBytes(msg));
            os.flush();
        } catch (IOException e) {
            System.out.println("IO Exception in sendMessage\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    //
    void stopConnection() {
        try {
            os.close();
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Something went wrong in Stop Connection\n" + e.getMessage());
            e.printStackTrace();
            //        }
        }
    }
}
