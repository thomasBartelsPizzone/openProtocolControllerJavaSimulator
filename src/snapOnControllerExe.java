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

public class snapOnControllerExe {
    public void start(int port) {
        System.out.println("We started");

        ServerSocket serverSocket = null;
        // Socket clientSocket = null;
        JTextArea textReceive = new JTextArea();
        JTextArea textReply = new JTextArea();
        JTextArea textManualReply = new JTextArea();
        try {
            // 1.a. create server
            serverSocket = new ServerSocket(port);
            while (true) {
                // 1.a. accept call?
                Socket clientSocket = (Socket) acceptCall(serverSocket);
                // 1.b. diaglog box
                createDialog(serverSocket, clientSocket, port, textReceive, textReply, textManualReply);
                appendDialog(textReceive, "***Listening on "+ port +"...\n");
                //
                new opClientHandler(serverSocket, clientSocket, port, textReceive, textReply, textManualReply).start();
                System.out.println("calling the handler again");
            }
        } catch (Throwable e) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
            System.out.println("Something went wrong in start\n" + e.getMessage() + "\n @ " + jsonTimestamp);
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
            stop(serverSocket);
        }
    }
    //
    Object acceptCall(ServerSocket serverSocket) {
        Socket sckt = null;
        try {
            sckt = serverSocket.accept();
            sckt.setSoTimeout((int) 1e5);
            sckt.setSoLinger(true, 1);

        } catch (Throwable e) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
            System.out.println("Something went wrong in acceptCall\n" + e.getMessage() + "\n @ " + jsonTimestamp);
            e.printStackTrace();
        }
        return sckt;
    }
    // class for client handling
    public static class opClientHandler extends Thread {
        public openProtocolClientParserMIDClass oPCP = new openProtocolClientParserMIDClass();
        public openProtocolClientSerializerMIDClass oPCS = new openProtocolClientSerializerMIDClass();
        public ServerSocket server;
        public Socket clientSocket;
        public int port;
        public BufferedReader readerIn;
        public DataOutputStream os;
        public InputStreamReader input;
        //
        public JTextArea textReceive;
        public JTextArea textReply;
        public JTextArea textManualReply;
        //
        public opClientHandler(ServerSocket servSocket, Socket socket, int portId, JTextArea tRec, JTextArea tRep, JTextArea tMRep) {
            this.clientSocket = socket;
            //
            this.port = portId;
            this.server = servSocket;
            //
            this.textReceive = tRec;
            this.textReply = tRep;
            this.textManualReply = tMRep;
        }
        //
        public void run() {
            System.out.println("called run again");
            String controllerMid = null;
            String inputCommandString = null;
            String ctrlReply = null;
            String errorCode = "01";
            List<Object> inputCommandList = new ArrayList<>();
            //
            String replyMessage = null;
            String inputLine = null;
            //
            HashMap<String, String> ctrlRespMessage = new HashMap<>();

            //ctrlRespMessage.put("MID0002", "00570002001000000000010001020103Airbag1000000000000000000\0");
            //ctrlRespMessage.put("MID0011", "00290011001000000000002001002\0");
            //ctrlRespMessage.put("MID0013", "010400130010000000000100102Airbag10000000000000000000310403050012000600150007001400080036009007201000480\0");
            //ctrlRespMessage.put("MID0031", "00260031001000000000020102\0");
            ///ctrlRespMessage.put("MID0033", "0103003300100000000001010225AsciiCharacters0000000003004000905000050600700800901000011012011315:011:0:22;\0");
            //ctrlRespMessage.put("MID0041", "0081004100000000000001C3412120000000025487960000032024-02-09:13:24:54046709190000\0");
            //ctrlRespMessage.put("MID0061", "02310061001000000000010001020103airbag700000000000000000004KPOL3456JKLO8970000000000050006003070000080000090100111120008401300140014001200150007391600000170999918000001900000202024-01-10:09:54:09212024-01-10:12:34:33221233456750000\0");
            //ctrlRespMessage.put("MID0065", "0118006500100000000001456789000002AIRBAG000000000000000000003001040020050060070080014670900046102001-04-22:14:54:34142112\0");
            ctrlRespMessage.put("MID0002", "00570002001000000000010001020103Airbag1000000000000000000");
            ctrlRespMessage.put("MID0011", "00290011001000000000002001002");
            // ctrlRespMessage.put("MID0013", "010400130010000000000100102Airbag10000000000000000000310403050012000600150007001400080036009007201000480");
            ctrlRespMessage.put("MID0013", "0104001300100000000001004027ft-lb                   0310401050007000600080007000700080000009000001000000 ");
            // ctrlRespMessage.put("MID0031", "00260031001000000000020102");
            ctrlRespMessage.put("MID0031", "0088003100200000000000160001000200030004000500060007000800090010001100120013001400150016 ");
            // ctrlRespMessage.put("MID0033", "0103003300100000000001010225AsciiCharacters00000000030040009050000506007008009010011012011315:011:0:22;");
            ctrlRespMessage.put("MID0033", "01000033001000000000011202PWR Steering Ln 17ft-lb  031040000050000006007108009110011011200:002:1:01;");
            // ctrlRespMessage.put("MID0041", "0081004100000000000001C3412120000000025487960000032024-02-09:13:24:54046709190000");
            ctrlRespMessage.put("MID0041", "01560041002000000000010120102625    020000000015032020-01-25:00:00:0004000000049005000000062020-01-25:00:00:000700000000000809090010100111.2.2               ");
            //ctrlRespMessage.put("MID0061", "02310061001000000000010001020103airbag700000000000000000004KPOL3456JKLO8970000000000050006003070000080000090100111120008401300140014001200150007391600000170999918000001900000202024-01-10:09:54:09212024-01-10:12:34:33221233456750000");
            ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000710250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002242000324300000440120102625    452024-02-27:16:40:12462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000720250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002342000324300000440120102625    452024-02-27:16:40:22462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000730250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002442000324300000440120102625    452024-02-27:16:40:35462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000730250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002542000324300000440120102625    452024-02-27:16:40:38462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000750250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002642000324300000440120102625    452024-02-27:16:40:49462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000750250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002742000324300000440120102625    452024-02-27:16:40:52462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            //ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000740250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002842000324300000440120102625    452024-02-27:16:40:54462024-02-07:11:58:20477ft-lb                   482490150                         51                         52                          ");
            ctrlRespMessage.put("MID0065", "0118006500100000000001456789000002AIRBAG000000000000000000003001040020050060070080014670900046102001-04-22:14:54:34142112");
            // ctrlRespMessage.put("MID0002", "00570002001000000000010001020103Airbag1000000000000000000\n");
            // ctrlRespMessage.put("MID0011", "00290011001000000000002001002\n");
            // ctrlRespMessage.put("MID0013", "010400130010000000000100102Airbag10000000000000000000310403050012000600150007001400080036009007201000480\n");
            // ctrlRespMessage.put("MID0031", "00260031001000000000020102\n");
            // ctrlRespMessage.put("MID0033", "0103003300100000000001010225AsciiCharacters0000000003004000905000050600700800901000011012011315:011:0:22;\n");
            // ctrlRespMessage.put("MID0041", "0081004100000000000001C3412120000000025487960000032024-02-09:13:24:54046709190000\n");
            // ctrlRespMessage.put("MID0061", "02310061001000000000010001020103airbag700000000000000000004KPOL3456JKLO8970000000000050006003070000080000090100111120008401300140014001200150007391600000170999918000001900000202024-01-10:09:54:09212024-01-10:12:34:33221233456750000\\n");
            // ctrlRespMessage.put("MID0065", "0118006500100000000001456789000002AIRBAG000000000000000000003001040020050060070080014670900046102001-04-22:14:54:34142112\n");
            //
            try {
                //2.b. get buffer reader / writer
                readerIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // dataInStr = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                input = new InputStreamReader(clientSocket.getInputStream());
                os = new DataOutputStream(clientSocket.getOutputStream());
                //
                System.out.println("in the while");
                boolean keepOpen = true;
                while (keepOpen) {
                    // inputLine = readBytes(dataInStr);
                    inputLine = readBytesFully(input);
                    if (inputLine != null && !"null".equals(inputLine) && !"".equals(inputLine)) {
                        //
                        System.out.println("client input command\n" + inputLine);
                        String integratorMid = oPCP.getMID(inputLine);
                        //
                        String midCommand = "MID" + integratorMid;
                        System.out.println("the mid command was: " + midCommand);

                        appendDialog(textReceive, "\nintegrator command: " + midCommand);
                        // appendDialog(textReceive, "input message: ");
                        appendDialog(textReceive, inputLine);
                        // Get the reply command if available
                        String answer = "acknowledge";
                        Class<?>[] integratorMidInputTypes = {String.class};
                        try {
                            Object integratorMidObj = Class.forName(midCommand).getDeclaredConstructor().newInstance();
                            Class<?> integratorMidClass = integratorMidObj.getClass();
                            Method integratorMidMethod = integratorMidClass.getDeclaredMethod("replyMID", integratorMidInputTypes);
                            try {
                                controllerMid = (String) integratorMidMethod.invoke(integratorMidObj, answer);
                                System.out.println("reply to integrator msg is " + controllerMid);
                                textReceive.append("controller Reply command: " + controllerMid);
                                // Build the reply
                                Class<?>[] controllerMidInputTypes = {String.class, List.class};
                                try {
                                    Object controllerMidObj = Class.forName(controllerMid).getDeclaredConstructor().newInstance();
                                    Class<?> controllerMidClass = controllerMidObj.getClass();
                                    Method controllerMidMethod = controllerMidClass.getDeclaredMethod("integratorString", controllerMidInputTypes);
                                    label:
                                    switch (Objects.requireNonNull(controllerMid)) {
                                        case "MID0005":
                                            inputCommandList.add(integratorMid);
                                            replyMessage = (String) controllerMidMethod.invoke(controllerMidObj, controllerMid.substring(3), inputCommandList);
                                            //
                                            break;
                                        case "MID0004":
                                            inputCommandList.add(integratorMid);
                                            inputCommandList.add(errorCode);
                                            replyMessage = (String) controllerMidMethod.invoke(controllerMidObj, controllerMid.substring(3), inputCommandList);
                                        case "MID0003":
                                            //close client socket
                                            keepOpen = false;
                                            break;
                                        case "MID9999":
                                            replyMessage = inputLine;
                                            // replyMessage = null;
                                            break;
                                        default:
                                            replyMessage = ctrlRespMessage.get(controllerMid);
                                            break;
                                    }
                                } catch (ClassNotFoundException e) {
                                    replyMessage = null;
                                    ZonedDateTime zonedDateTime = ZonedDateTime.now();
                                    //String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
                                    String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                                    System.out.println("building reply message from class's serializer\n" + e.getMessage() + "\n @ " + jsonTimestamp);
                                    e.printStackTrace();
                                    // break;
                                }
                                //4. update dialog box with message.
                                //  Thread.sleep(3500);
                                //  Thread.sleep(1500);
                                //  Thread.sleep(7500);
                                //  Thread.sleep(25500);
                                appendDialog(textReply, "reply message:\n" + replyMessage + "\n");
                                os.write(msgBytes(replyMessage + "\0"));
                                //                            os.write(msgBytes(replyMessage+"\0"+"\n"));
                                // os.write(msgBytes(replyMessage+"NUL"));
                                os.flush();
                                System.out.println("wrote reply");
                                System.out.println(replyMessage);
                            } catch (NullPointerException n) {
                                System.out.println("this should close the while");
                                ZonedDateTime zonedDateTime = ZonedDateTime.now();

                                String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                                System.out.println("@ " + jsonTimestamp);
                                replyMessage = null;
                                keepOpen = false;
                                //break;
                                //  Thread.sleep(1500);
                                //  Thread.sleep(25500);
//                                os.write(msgBytes(replyMessage + "\0"));
                                os.flush();
                                break;
                            }
                        } catch (ClassNotFoundException | NoSuchMethodException e) {
                            ZonedDateTime zonedDateTime = ZonedDateTime.now();

                            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                            System.out.println("reply command from integrator class failed\n" + e.getMessage() + "\n @ " + jsonTimestamp);
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("empty input string");
                        System.out.println("client input command\n" + inputLine);
                    }
                }
                appendDialog(textReceive, "\n");
                os.close();
            } catch (IOException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
                System.out.println("Something went wrong in run\n" + e.getMessage() + "\n @ " + jsonTimestamp);
                e.printStackTrace();
            }
        }
        //
        protected String readBytesFully(InputStreamReader in) throws IOException, IllegalArgumentException {
            System.out.println("in readBytesFully");

            String returnString = null;
            int n = 0;
            int maxStr = 9980;
            int[] bS = new int[maxStr];
            // boolean cont = true;
            // while (cont && n < maxStr) {
            while (n < maxStr) {
                int b = in.read();
                if (b == 0) {
                    //cont = false;
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
    }
    //
    static void appendDialog(JTextArea tArea, String dialog){
        tArea.append(dialog);
        tArea.append("\n");
    }
    //
    public static void createDialog(ServerSocket serverSocket, Socket clientSocket, int port, JTextArea textReceive, JTextArea textReply, JTextArea textManualReply) {
        //
        JFrame frame = new JFrame("Controller " + serverSocket.getInetAddress().getHostName() + ": " + port);
        JPanel mainContainer = new JPanel();
        // frame.setSize(800, 1280);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
        //
        textReceive.setLineWrap(true);
        textReceive.setColumns(100);
        textReceive.setRows(15);
        // test
        appendDialog(textReceive, serverSocket + ": " + port);
        JPanel pnlReceive = new JPanel();
        pnlReceive.setLayout(new BorderLayout());
        JScrollPane scrollReceive = new JScrollPane(textReceive);
        pnlReceive.add(scrollReceive, BorderLayout.CENTER);
        pnlReceive.setBorder(BorderFactory.createEtchedBorder());
        //
        textReply.setLineWrap(true);
        textReply.setColumns(100);
        textReply.setRows(15);
        JPanel pnlSend = new JPanel();
        pnlSend.setLayout(new BorderLayout());
        JScrollPane scrollSend = new JScrollPane(textReply);
        pnlSend.add(scrollSend, BorderLayout.CENTER);
        pnlSend.setBorder(BorderFactory.createEtchedBorder());
        //
        textManualReply.setLineWrap(true);
        textManualReply.setColumns(100);
        textManualReply.setRows(10);
        JPanel pnlManualSend = new JPanel();
        pnlManualSend.setLayout(new BorderLayout());
        JScrollPane scrollManualSend = new JScrollPane(textManualReply);
        pnlManualSend.add(scrollManualSend, BorderLayout.CENTER);
        pnlManualSend.setBorder(BorderFactory.createEtchedBorder());
        //
        JButton exit = new JButton("Exit");
        JButton manualSend = new JButton("Send");
        //
        JPanel pnlEnd = new JPanel();
        //
        pnlEnd.add(exit);
        pnlEnd.add(manualSend);
        //
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(pnlReceive);
        mainContainer.add(pnlSend);
        mainContainer.add(pnlManualSend);
        mainContainer.add(pnlEnd);
        //
        frame.add(mainContainer);
        frame.pack();

        frame.setVisible(true);

        manualSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                try {
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    appendDialog(textReply, "reply message:\n" + textManualReply.getText() + "\n");
                    os.write(msgBytes(textManualReply.getText() + "\0"));
                    System.out.println("wrote bytes");
                    os.flush();
                } catch (IOException o) {
                    ZonedDateTime zonedDateTime = ZonedDateTime.now();
                    //
                    String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                    System.out.println("IO Exception in dialog box send button\n" + o.getMessage() + "\n @ " + jsonTimestamp);
                    o.printStackTrace();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }
    //
    public static byte[] msgBytes(String serializedMsg) {
        byte[] bytes = serializedMsg.getBytes(StandardCharsets.US_ASCII);
        return bytes;
    }
    //
    public static void stop(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    public static void main(String[] args) {
        snapOnControllerExe server = new snapOnControllerExe();
        //
        // set port Id
        server.start(4545);
    }
}