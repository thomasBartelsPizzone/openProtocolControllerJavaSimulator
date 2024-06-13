import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class controllerMessaging {
//    ServerSocket serverSocket;
    DataOutputStream os;
    InputStreamReader input;
    Socket clientSocket;
//    controllerExe ctrl = new controllerExe();

//    Socket openConnection(int port, JTextArea textReceive) {
//    void openConnection(int port, JTextArea textReceive) {
    public void openConnection(ServerSocket serverSocket, int port, JTextArea textReceive) {
//        serverSocket = serverSocket;
//        Socket clientSocket = null;
        try {
            // 1.a. create server
//            serverSocket = new ServerSocket(port);
            // 1.b. accept call?
            clientSocket = (Socket) acceptCall(serverSocket);
            //
            controllerExe.appendDialog(textReceive, "controller connected to " + clientSocket.getRemoteSocketAddress());
            controllerLogger.controllerLog("controller2Host", "controller connected to " + clientSocket.getRemoteSocketAddress(), "info");
            // 2. create objects for
            input = new InputStreamReader(clientSocket.getInputStream());
            os = new DataOutputStream(clientSocket.getOutputStream());

//        } catch (IOException e) {
        } catch (Throwable e) {
            controllerLogger.controllerLog("startConnection", e.getMessage(), "error");
            System.out.println("Something went wrong in startConnection Or keepAlive\n" + e.getMessage());
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
//            stop(serverSocket);
            stop();
        }
//        return clientSocket;
    }
    //
    Object acceptCall(ServerSocket serverSocket) {
        //Object acceptCall() {
        Socket sckt = null;
        try {
            sckt = serverSocket.accept();
            sckt.setSoTimeout((int) 1e3);
            sckt.setSoLinger(true, 1);

        } catch (Throwable e) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
            System.out.println("Something went wrong in acceptCall\n" + e.getMessage() + "\n @ " + jsonTimestamp);
            e.printStackTrace();
        }
        assert sckt != null;
        System.out.println(sckt.getRemoteSocketAddress());
        System.out.println(sckt.getLocalAddress());
        return sckt;
    }
    //
    String sendMessage(String msg) {
        try {
            os.write(msgBytes(msg));
            os.flush();
            String inputStreamReaderReturn = readFully(input);
            System.out.println("the InputStreamReader Method: " + inputStreamReaderReturn);
            return inputStreamReaderReturn;
        } catch (IOException e) {
            System.out.println("IO Exception in sendMessage\n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    //
    String readFully(InputStreamReader in) throws IOException {
        String returnString;
        int n = 0;
        int maxStr = 9999;
        int[] bS = new int[maxStr];
        //boolean cont = true;
        // while (cont && n < maxStr) {
        while (n < maxStr) {
            int b = in.read();
            if (b == 0) {
                // cont = false;
                break;
            } else if (b != -1) {
                bS[n] = b;
            } else {
                // cont = false;
                break;
            }
            n += 1;
        }
        returnString = new String(bS, 0, n);
        System.out.println("returnString\n" + returnString);
        return returnString;
    }
    //
    byte[] msgBytes(String serializedMsg) {
        return serializedMsg.getBytes(StandardCharsets.US_ASCII);
    }
    //
    void stop() {
//        void stop(ServerSocket serverSocket) {
        try {
            clientSocket.close();
//            controllerExe.appendDialog(dialogBox, "Closed connection on:\n" + inputIdAddress + ": " + inputPortId);
//            controllerLogger.controllerLog("controller2Host", "host closed connected to " + inputIdAddress + ": " + inputPortId, "info");
//            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
