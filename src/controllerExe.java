import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class controllerExe {
    JTextField ctrlName;
    JTextField portString;
    static int controllerPortId;
    JTextField initTimeout;
    JTextField timeoutDelay;
    String[][] midReplyCmds;
    JTextArea replyTextArea;
    JTextField searchField;
    JComboBox<String> filteredReplies;
    JTextField selectedReplyField;
    JTextField serializedMidString;
    JTextArea dialogBox;
    JButton btnMode;
    JButton btnSendSerializedMid;
    openProtocolClientParserMIDClass oPCP = new openProtocolClientParserMIDClass();
    openProtocolClientSerializerMIDClass oPCS = new openProtocolClientSerializerMIDClass();
    // Controller Frame
    JFrame controllerFrame = new JFrame("Controller");
    ServerSocket serverSocket;
    public String[][] relpyCmdList() {
        return new String[][]{//{"       ", "       "},
                {"1", "MID0011", "00290011001000000000002001002"},
                {"1", "MID0013", "0104001300100000000001004028N-m                     0310401050007000600080007000700080000009000001000000 "},
                {"1", "MID0031", "0088003100200000000000160001000200030004000500060007000800090010001100120013001400150016 "},
                {"1", "MID0033", "01000033001000000000011202PWR Steering Ln 18N-m    031040000050000006007108009110011011200:002:1:01;"},
                {"1", "MID0041", "01560041002000000000010120102625    020000000015032020-01-25:00:00:0004000000049005000000062020-01-25:00:00:000700000000000809090010100111.2.2               "},
                {"1", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000710250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002242000324300000440120102625    452024-02-27:16:40:12462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"2", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000720250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002342000324300000440120102625    452024-02-27:16:40:22462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"3", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000730250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002442000324300000440120102625    452024-02-27:16:40:35462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"4", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000730250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002542000324300000440120102625    452024-02-27:16:40:38462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"5", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000750250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002642000324300000440120102625    452024-02-27:16:40:49462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"6", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000750250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002742000324300000440120102625    452024-02-27:16:40:52462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"7", "MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000740250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002842000324300000440120102625    452024-02-27:16:40:54462024-02-07:11:58:20478N-m                     478512963                         51                         52                          "},
                {"1", "MID0065", "0118006500100000000001456789000002pAirBg000000000000000000003001040020050060070080014670900046102005-06-24:13:29:12142112"},

        };
    }
    //
    public void start(int port) {
        System.out.println("We started");

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
            String controllerMid;
            String errorCode = "01";
            List<Object> inputCommandList = new ArrayList<>();
            //
            String replyMessage = null;
            String inputLine;
            //
            HashMap<String, String> ctrlRespMessage = new HashMap<>();

            ctrlRespMessage.put("MID0002", "00570002001000000000010001020103pAirBg1000000000000000000");
            ctrlRespMessage.put("MID0011", "00290011001000000000002001002");
            // ctrlRespMessage.put("MID0013", "010400130010000000000100102pAirBg10000000000000000000310403050012000600150007001400080036009007201000480");
            ctrlRespMessage.put("MID0013", "0104001300100000000001004028N-m                     0310401050007000600080007000700080000009000001000000 ");
            // ctrlRespMessage.put("MID0031", "00260031001000000000020102");
            ctrlRespMessage.put("MID0031", "0088003100200000000000160001000200030004000500060007000800090010001100120013001400150016 ");
            // ctrlRespMessage.put("MID0033", "0103003300100000000001010225AsciiCharacters00000000030040009050000506007008009010011012011315:011:0:22;");
            ctrlRespMessage.put("MID0033", "01000033001000000000011202PWR Steering Ln 18N-m    031040000050000006007108009110011011200:002:1:01;");
            // ctrlRespMessage.put("MID0041", "0081004100000000000001C3412120000000025487960000032024-05-06:13:24:54046709190000");
            ctrlRespMessage.put("MID0041", "01560041002000000000010120102625    020000000015032020-01-25:00:00:0004000000049005000000062020-01-25:00:00:000700000000000809090010100111.2.2               ");
            //ctrlRespMessage.put("MID0061", "02310061001000000000010001020103pAirBg300000000000000000004K7B9X3P2DRFGHJQ0000000000050006003070000080000090100111120008401300140014001200150007391600000170999918000001900000202024-01-10:09:54:09212024-01-10:12:34:33221233456750000");
            ctrlRespMessage.put("MID0061", "050000610041        010000020003                         04                         050005060040710080000509000110000111112113114115116117118019120000000000021000700220008002300070024000710250000026000002700000280000029000003000000310000032000330003400035000000360000003700000038000000390000004000000041000000002242000324300000440120102625    452024-02-27:16:40:12462024-02-07:11:58:20478N-m                     478512963                         51                         52                          ");
            ctrlRespMessage.put("MID0065", "0118006500100000000001456789000002pAirBg000000000000000000003001040020050060070080014670900046102005-06-24:13:29:12142112");
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
    public JPanel connectionPanel()  {
        // Connection JPanels
        JPanel pnlCnctMain = new JPanel();
        pnlCnctMain.setLayout(new BoxLayout(pnlCnctMain, BoxLayout.Y_AXIS));
        pnlCnctMain.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCnctMain.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Connection Header
        JLabel lblManualConnect = new JLabel("Device Details");
//        lblManualConnect.setFont(new Font("Courier", Font.BOLD, 16));
        lblManualConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblManualConnect.setAlignmentY(Component.TOP_ALIGNMENT);
        lblManualConnect.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlCnctMain.add(lblManualConnect);
        // Connect Detail
        JPanel pnlCnctDtl = new JPanel();
        pnlCnctDtl.setLayout(new BoxLayout(pnlCnctDtl, BoxLayout.X_AXIS));
        // labels
        JPanel pnlCnctLbls = new JPanel();
        pnlCnctLbls.setPreferredSize(new Dimension(85, 70));
        pnlCnctLbls.setMaximumSize(pnlCnctLbls.getPreferredSize());
        pnlCnctLbls.setLayout(new BoxLayout(pnlCnctLbls, BoxLayout.Y_AXIS));
        pnlCnctLbls.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlCnctipLbl = new JPanel();
        pnlCnctipLbl.setMaximumSize(new Dimension(85, 30));
        pnlCnctipLbl.setLayout(new BoxLayout(pnlCnctipLbl, BoxLayout.X_AXIS));
        JLabel ipLbl = new JLabel("Port:");
        ipLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCnctipLbl.add(ipLbl);
        pnlCnctLbls.add(pnlCnctipLbl);
        JPanel pnlCnctLblpd = new JPanel();
        pnlCnctLblpd.setMaximumSize(new Dimension(85, 5));
        pnlCnctLblpd.setMinimumSize(new Dimension(50, 5));
        pnlCnctLbls.add(pnlCnctLblpd);
        JPanel pnlCnctlblCtrl = new JPanel();
        pnlCnctlblCtrl.setMaximumSize(new Dimension(85, 30));
        pnlCnctlblCtrl.setMinimumSize(new Dimension(50, 30));
        pnlCnctlblCtrl.setLayout(new BoxLayout(pnlCnctlblCtrl, BoxLayout.X_AXIS));
        JLabel lblCtrl = new JLabel("Device:");
        lblCtrl.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCnctlblCtrl.add(lblCtrl);
        pnlCnctLbls.add(pnlCnctlblCtrl);
        pnlCnctDtl.add(pnlCnctLbls);
        // ip, port text areas (x) + device (y)
        JPanel pnlCnctDtlFlds = new JPanel();
        pnlCnctDtlFlds.setLayout(new BoxLayout(pnlCnctDtlFlds, BoxLayout.Y_AXIS));
        pnlCnctDtlFlds.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlCnctDtlFlds.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlCnctDtlPortFld = new JPanel();
//        pnlCnctDtlPortFld.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 30));
//        pnlCnctDtlPortFld.setMaximumSize(pnlCnctDtlPortFld.getPreferredSize());
//        pnlCnctDtlPortFld.setLayout(new BoxLayout(pnlCnctDtlPortFld, BoxLayout.X_AXIS));
        portString = new JTextField("4545");
        portString.setToolTipText("Device/Controller Port Id required.");
        portString.setAlignmentX(Component.LEFT_ALIGNMENT);
        portString.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlCnctDtlPortFld.add(portString);
        pnlCnctDtlFlds.add(pnlCnctDtlPortFld);
        JPanel pnlCnctDtlFldspd = new JPanel();
//        pnlCnctDtlFldspd.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 5));
//        pnlCnctDtlFldspd.setMaximumSize(pnlCnctDtlFldspd.getPreferredSize());
        pnlCnctDtlFlds.add(pnlCnctDtlFldspd);
        JPanel pnlCnctDtlctrlNameFlds = new JPanel();
//        pnlCnctDtlctrlNameFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 30));
//        pnlCnctDtlctrlNameFlds.setMaximumSize(pnlCnctDtlctrlNameFlds.getPreferredSize());
        pnlCnctDtlctrlNameFlds.setLayout(new BoxLayout(pnlCnctDtlctrlNameFlds, BoxLayout.X_AXIS));
        ctrlName = new JTextField("deviceName");
        ctrlName.setToolTipText("Device/Controller Name.");
        ctrlName.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctrlName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlCnctDtlctrlNameFlds.add(ctrlName);
        pnlCnctDtlFlds.add(pnlCnctDtlctrlNameFlds);
        pnlCnctDtl.add(pnlCnctDtlFlds);
        pnlCnctMain.add(pnlCnctDtl);
        //
        return pnlCnctMain;
    }
    //
    public JPanel timingPanel() {
        // Timing JPanels
        JPanel pnlTmMain = new JPanel();
        pnlTmMain.setLayout(new BoxLayout(pnlTmMain, BoxLayout.Y_AXIS));
        pnlTmMain.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlTmMain.setAlignmentY(Component.CENTER_ALIGNMENT);
//        pnlTmMain.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        // Connection Header
        JLabel lblTime = new JLabel("Keep Alive");
        lblTime.setFont(new Font("Courier", Font.BOLD, 16));
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTime.setAlignmentY(Component.TOP_ALIGNMENT);
        lblTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlTmMain.add(lblTime);
        // Timing Detail
        JPanel pnlTmDtl = new JPanel();
        pnlTmDtl.setLayout(new BoxLayout(pnlTmDtl, BoxLayout.X_AXIS));
        // labels
        JPanel pnlTmLbls = new JPanel();
        pnlTmLbls.setPreferredSize(new Dimension(85, 70));
        pnlTmLbls.setMaximumSize(pnlTmLbls.getPreferredSize());
        pnlTmLbls.setMinimumSize(pnlTmLbls.getPreferredSize());
        pnlTmLbls.setLayout(new BoxLayout(pnlTmLbls, BoxLayout.Y_AXIS));
        pnlTmLbls.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlTmDelay = new JPanel();
        pnlTmDelay.setPreferredSize(new Dimension(85, 30));
        pnlTmDelay.setMaximumSize(pnlTmDelay.getPreferredSize());
        pnlTmDelay.setMinimumSize(pnlTmDelay.getPreferredSize());
        pnlTmDelay.setLayout(new BoxLayout(pnlTmDelay, BoxLayout.X_AXIS));
        JLabel lblInitDelay = new JLabel("Delay:");
        lblInitDelay.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlTmDelay.add(lblInitDelay);
        pnlTmLbls.add(pnlTmDelay);
        JPanel pnlTmLblpd = new JPanel();
        pnlTmLblpd.setPreferredSize(new Dimension(85, 5));
        pnlTmLblpd.setMaximumSize(pnlTmLblpd.getPreferredSize());
        pnlTmLblpd.setMinimumSize(pnlTmLblpd.getPreferredSize());
        pnlTmLbls.add(pnlTmLblpd);
        JPanel pnlTmPrd = new JPanel();
        pnlTmPrd.setPreferredSize(new Dimension(85, 30));
        pnlTmPrd.setMaximumSize(pnlTmPrd.getPreferredSize());
        pnlTmPrd.setMinimumSize(pnlTmPrd.getPreferredSize());
        pnlTmPrd.setLayout(new BoxLayout(pnlTmPrd, BoxLayout.X_AXIS));
        JLabel lblTimeoutPrd = new JLabel("Timeout:");
        lblTimeoutPrd.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlTmPrd.add(lblTimeoutPrd);
        pnlTmLbls.add(pnlTmPrd);
        pnlTmDtl.add(pnlTmLbls);
        // delay and timeout text areas
        JPanel pnlTmDtlFlds = new JPanel();
        pnlTmDtlFlds.setPreferredSize(new Dimension(85, 70));
        pnlTmDtlFlds.setMaximumSize(pnlTmDtlFlds.getPreferredSize());
        pnlTmDtlFlds.setMinimumSize(pnlTmDtlFlds.getPreferredSize());
        pnlTmDtlFlds.setLayout(new BoxLayout(pnlTmDtlFlds, BoxLayout.Y_AXIS));
        pnlTmDtlFlds.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlTmDtlInitTmFlds = new JPanel();
        pnlTmDtlInitTmFlds.setPreferredSize(new Dimension(85, 30));
        pnlTmDtlInitTmFlds.setMaximumSize(pnlTmDtlInitTmFlds.getPreferredSize());
        pnlTmDtlInitTmFlds.setMinimumSize(pnlTmDtlInitTmFlds.getPreferredSize());
        pnlTmDtlInitTmFlds.setLayout(new BoxLayout(pnlTmDtlInitTmFlds, BoxLayout.X_AXIS));
        initTimeout = new JTextField("75.0");
        initTimeout.setPreferredSize(new Dimension(30, 30));
        initTimeout.setMaximumSize(initTimeout.getPreferredSize());
        initTimeout.setMinimumSize(initTimeout.getPreferredSize());
        initTimeout.setLayout(new BoxLayout(initTimeout, BoxLayout.X_AXIS));
        initTimeout.setToolTipText("Initial delay period before sending keep alive message.");
        initTimeout.setAlignmentX(Component.LEFT_ALIGNMENT);
        initTimeout.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));
        pnlTmDtlInitTmFlds.add(initTimeout);
        JTextField InitTmUnit = new JTextField("sec.");
        InitTmUnit.setPreferredSize(new Dimension(50, 30));
        InitTmUnit.setMaximumSize(InitTmUnit.getPreferredSize());
        InitTmUnit.setMinimumSize(InitTmUnit.getPreferredSize());
        InitTmUnit.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
        InitTmUnit.setBackground(Color.white);
        InitTmUnit.setLayout(new BoxLayout(InitTmUnit, BoxLayout.X_AXIS));
        InitTmUnit.setAlignmentX(Component.CENTER_ALIGNMENT);
        InitTmUnit.setEditable(false);
        pnlTmDtlInitTmFlds.add(InitTmUnit);
        pnlTmDtlFlds.add(pnlTmDtlInitTmFlds);
        JPanel pnlTmDtlFldsPd = new JPanel();
        pnlTmDtlFldsPd.setPreferredSize(new Dimension(85, 5));
        pnlTmDtlFldsPd.setMaximumSize(pnlTmDtlFldsPd.getPreferredSize());
        pnlTmDtlFldsPd.setMinimumSize(pnlTmDtlFldsPd.getPreferredSize());
        pnlTmDtlFlds.add(pnlTmDtlFldsPd);
        JPanel pnlTmDtlTmDlyFlds = new JPanel();
        pnlTmDtlTmDlyFlds.setPreferredSize(new Dimension(85, 30));
        pnlTmDtlTmDlyFlds.setMaximumSize(pnlTmDtlTmDlyFlds.getPreferredSize());
        pnlTmDtlTmDlyFlds.setMinimumSize(pnlTmDtlTmDlyFlds.getPreferredSize());
        pnlTmDtlTmDlyFlds.setLayout(new BoxLayout(pnlTmDtlTmDlyFlds, BoxLayout.X_AXIS));
        timeoutDelay = new JTextField("65.0");
        timeoutDelay.setPreferredSize(new Dimension(30, 30));
        timeoutDelay.setMaximumSize(timeoutDelay.getPreferredSize());
        timeoutDelay.setMinimumSize(timeoutDelay.getPreferredSize());
        timeoutDelay.setLayout(new BoxLayout(timeoutDelay, BoxLayout.X_AXIS));
        timeoutDelay.setToolTipText("Period between sending keep alive message.");
        timeoutDelay.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeoutDelay.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
        pnlTmDtlTmDlyFlds.add(timeoutDelay);
        JTextField TmDlyUnit = new JTextField("sec.");
        TmDlyUnit.setPreferredSize(new Dimension(50, 30));
        TmDlyUnit.setMaximumSize(TmDlyUnit.getPreferredSize());
        TmDlyUnit.setMinimumSize(TmDlyUnit.getPreferredSize());
        TmDlyUnit.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
        TmDlyUnit.setBackground(Color.white);
        TmDlyUnit.setLayout(new BoxLayout(TmDlyUnit, BoxLayout.X_AXIS));
        TmDlyUnit.setAlignmentX(Component.CENTER_ALIGNMENT);
        TmDlyUnit.setEditable(false);
        pnlTmDtlTmDlyFlds.add(TmDlyUnit);
        pnlTmDtlFlds.add(pnlTmDtlTmDlyFlds);
        pnlTmDtl.add(pnlTmDtlFlds);
        pnlTmMain.add(pnlTmDtl);
        //
        return pnlTmMain;
    }
    //
    // Function to update JTextArea content based on filtering
    void updateReplyTextArea() {
        String searchText = searchField.getText().toLowerCase();
        DefaultComboBoxModel<String> filteredModel = new DefaultComboBoxModel<>();
        StringBuilder textAreaContent = new StringBuilder();
        for (String[] reply : midReplyCmds) {
            String value1 = reply[1].toLowerCase();
            String value2 = reply[2].toLowerCase();
            if (searchText.isEmpty() || value1.contains(searchText) || value2.contains(searchText)) {
                textAreaContent.append(reply[1] + ", " + reply[2] + "\n");
                filteredModel.addElement(reply[1] + ", " + reply[2]);
            }
        }
        replyTextArea.setText(textAreaContent.toString());
        filteredReplies.setModel(filteredModel);
        filteredReplies.setEnabled(filteredModel.getSize() > 0); // Enable only if there are results
    }
    //
    public JPanel midPanel(double pnlWRatio2) {
        // MID section
        JPanel pnlMidContainer = new JPanel();
        pnlMidContainer.setLayout(new BoxLayout(pnlMidContainer, BoxLayout.Y_AXIS));
        pnlMidContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlMidContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
        // MID Header
        JLabel lblMessages = new JLabel("MID Messages");
        lblMessages.setFont(new Font("Courier", Font.BOLD, 14));
        lblMessages.setAlignmentY(Component.TOP_ALIGNMENT);
        lblMessages.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMessages.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlMidContainer.add(lblMessages);
        // mid command container
        JPanel pnlMidCommand = new JPanel();
        pnlMidCommand.setLayout(new BoxLayout(pnlMidCommand, BoxLayout.X_AXIS));
        pnlMidCommand.setAlignmentX(Component.LEFT_ALIGNMENT);
//      // labels
        JPanel pnlAllMIDLbls = new JPanel();
        pnlAllMIDLbls.setPreferredSize(new Dimension(150, 70));
        pnlAllMIDLbls.setMaximumSize(pnlAllMIDLbls.getPreferredSize());
        pnlAllMIDLbls.setMinimumSize(new Dimension(100, 70));
        pnlAllMIDLbls.setLayout(new BoxLayout(pnlAllMIDLbls, BoxLayout.Y_AXIS));
        pnlAllMIDLbls.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlMIDLbl = new JPanel();
        pnlMIDLbl.setMaximumSize(new Dimension(150, 30));
        pnlMIDLbl.setMinimumSize(new Dimension(100, 30));
        pnlMIDLbl.setLayout(new BoxLayout(pnlMIDLbl, BoxLayout.X_AXIS));
        JLabel lblMID = new JLabel("MID Command:");
        lblMID.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlMIDLbl.add(lblMID);
        pnlAllMIDLbls.add(pnlMIDLbl);
        JPanel pnlAllMIDpd = new JPanel();
        pnlAllMIDpd.setMaximumSize(new Dimension(150, 5));
        pnlAllMIDpd.setMinimumSize(new Dimension(100, 5));
        pnlAllMIDLbls.add(pnlAllMIDpd);
        JPanel pnlSerializedMIDLbl = new JPanel();
        pnlSerializedMIDLbl.setMaximumSize(new Dimension(150, 30));
        pnlSerializedMIDLbl.setMinimumSize(new Dimension(100, 30));
        pnlSerializedMIDLbl.setLayout(new BoxLayout(pnlSerializedMIDLbl, BoxLayout.X_AXIS));
        JLabel lblSerialized = new JLabel("Serialized MID:");
        lblSerialized.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSerializedMIDLbl.add(lblSerialized);
        pnlAllMIDLbls.add(pnlSerializedMIDLbl);
        pnlMidCommand.add(pnlAllMIDLbls);
        // MID Command and input vals section
        JPanel pnlMIDDtlFlds = new JPanel();
        pnlMIDDtlFlds.setLayout(new BoxLayout(pnlMIDDtlFlds, BoxLayout.Y_AXIS));
        pnlMIDDtlFlds.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel pnlMIDDtlCmdInVFlds = new JPanel();
//        pnlMIDDtlCmdInVFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 30));
        pnlMIDDtlCmdInVFlds.setMaximumSize(pnlMIDDtlCmdInVFlds.getPreferredSize());
        pnlMIDDtlCmdInVFlds.setLayout(new BoxLayout(pnlMIDDtlCmdInVFlds, BoxLayout.X_AXIS));
        // call method to get list of commands as dropdown.
        midReplyCmds = relpyCmdList();
        replyTextArea = new JTextArea();
        replyTextArea.setEditable(false);
        // Text field for search (editable)
        searchField = new JTextField();
        // Combo box for filtered results (uneditable)
        filteredReplies = new JComboBox<>();
        filteredReplies.setEnabled(false); // Initially disable until there are results
        // Parallel text field for selection (editable)
        selectedReplyField = new JTextField();
        // Document listener for search field
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateReplyTextArea();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateReplyTextArea();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateReplyTextArea();
            }
        });
        // Handle double click on filtered reply combo box
        filteredReplies.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedReply = (String) filteredReplies.getSelectedItem();
                    selectedReplyField.setText(selectedReply);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        //
        pnlMIDDtlCmdInVFlds.add(replyTextArea);
        pnlMIDDtlCmdInVFlds.add(searchField);
        pnlMIDDtlCmdInVFlds.add(filteredReplies);
        pnlMIDDtlCmdInVFlds.add(selectedReplyField);
        pnlMIDDtlFlds.add(pnlMIDDtlCmdInVFlds);
        JPanel pnlMIDDtlFldsPd = new JPanel();
//        pnlMIDDtlFldsPd.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 5));
        pnlMIDDtlFldsPd.setMaximumSize(pnlMIDDtlFldsPd.getPreferredSize());
        pnlMIDDtlFlds.add(pnlMIDDtlFldsPd);
        JPanel pnlMIDDtlSrlzdFlds = new JPanel();
//        pnlMIDDtlSrlzdFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 30));
        pnlMIDDtlSrlzdFlds.setMaximumSize(pnlMIDDtlSrlzdFlds.getPreferredSize());
        pnlMIDDtlSrlzdFlds.setLayout(new BoxLayout(pnlMIDDtlSrlzdFlds, BoxLayout.X_AXIS));
        serializedMidString = new JTextField();
        serializedMidString.setToolTipText("include serialized MID message.");
        serializedMidString.setAlignmentX(Component.LEFT_ALIGNMENT);
        serializedMidString.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlMIDDtlSrlzdFlds.add(serializedMidString);
        pnlMIDDtlFlds.add(pnlMIDDtlSrlzdFlds);
        pnlMidCommand.add(pnlMIDDtlFlds);
        pnlMidContainer.add(pnlMidCommand);
        //
        return pnlMidContainer;
    }
    //
    public JPanel sendPanel() {
        // MID Send JPanels
        JPanel pnlSendMID = new JPanel();
        pnlSendMID.setLayout(new BoxLayout(pnlSendMID, BoxLayout.Y_AXIS));
        pnlSendMID.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSendMID.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Connection Header
        JLabel blankLabel = new JLabel(" ");
        blankLabel.setFont(new Font("Courier", Font.BOLD, 14));
        blankLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        blankLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        blankLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlSendMID.add(blankLabel);
        // main mid container
//        btnSendSerializedMid = new JButton("send");
//        btnSendSerializedMid.setPreferredSize(new Dimension(100, 30));
//        btnSendSerializedMid.setMaximumSize(btnSendSerializedMid.getPreferredSize());
//        btnSendSerializedMid.setMinimumSize(btnSendSerializedMid.getPreferredSize());
//        btnSendSerializedMid.setAlignmentX(Component.LEFT_ALIGNMENT);
//        pnlSendMID.add(btnSendSerializedMid);
        //
        return pnlSendMID;
    }
    //
    public JPanel dialogPanel(double pnlHRatio1, double pnlWRatio1) {
        // Dialog section
        JPanel pnlDialogContainer = new JPanel();
        pnlDialogContainer.setLayout(new BoxLayout(pnlDialogContainer, BoxLayout.Y_AXIS));
        pnlDialogContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Dialog Header
        JLabel lblDialog = new JLabel("Communication Log");
        lblDialog.setFont(new Font("Courier", Font.BOLD, 14));
        lblDialog.setAlignmentY(Component.TOP_ALIGNMENT);
        lblDialog.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDialog.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlDialogContainer.add(lblDialog);
        // mid command container
        JPanel pnldialogBox = new JPanel();
        pnldialogBox.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), (int) (controllerFrame.getHeight() * pnlHRatio1)));
        pnldialogBox.setLayout(new BoxLayout(pnldialogBox, BoxLayout.X_AXIS));
        pnldialogBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        dialogBox = new JTextArea();
        dialogBox.setLineWrap(true);
        dialogBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollDialogContainer = new JScrollPane(dialogBox);
        pnldialogBox.add(scrollDialogContainer);
        pnlDialogContainer.add(pnldialogBox);
        return pnlDialogContainer;
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
        textReceive.setRows(20);
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
        textReply.setRows(25);
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
    public void createFrame() {
        // ScreenSize
        Dimension refScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int refFrameSizeW = (int) refScreenSize.getWidth();
        int refFrameSizeH = (int) refScreenSize.getHeight();
        // frame ratio
        double maxFramePercentageW = 0.80;
        double maxFramePercentageH = 0.80;
        double minFramePercentageW = 0.65;
        double minFramePercentageH = 0.65;
        int maxFrameSizeW = (int) (refFrameSizeW * maxFramePercentageW);
        int minFrameSizeW = (int) (refFrameSizeW * minFramePercentageW);
        int maxFrameSizeH = (int) (refFrameSizeH * maxFramePercentageH);
        int minFrameSizeH = (int) (refFrameSizeH * minFramePercentageH);

        controllerFrame.setPreferredSize(new Dimension(minFrameSizeW, minFrameSizeH));
        controllerFrame.setMaximumSize(new Dimension(maxFrameSizeW, maxFrameSizeH));
        controllerFrame.setMinimumSize(controllerFrame.getPreferredSize());
        controllerFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                hostLogger.hostLog("exit", "closing window", "info");
                try {
                    hostLogger.hostLog("exit", "closing window", "info");
                    stop(serverSocket);
                } catch (Throwable a) {
                    hostLogger.hostLog("exit", a.getMessage(), "error");
//                    System.out.println("Closing Window, can't stop connection\n" + a.getMessage());
//                    a.printStackTrace();
                }
                System.exit(0);
            }
        });
        // panel ratios
        double pnlHRatioA = (double) 2/9;
        double pnlHRatioB = (double) 1/18;
//        double pnlHRatioB = (double) 1/9;
        double pnlHRatioC = (double) 1/3;
        double pnlHRatioD = (double) 1/36;
        double pnlHRatioE = (double) 1/9;

        double pnlWRatioA = (double) 11/12;
        double pnlWRatioB = (double) 2/3;
        double pnlWRatioC = (double) 5/24;
        double pnlWRatioD = (double) 1/3;
        double pnlWRatioE = (double) 1/8;
        double pnlWRatioF = (double) 3/4;
//        double pnlWRatioG = (double) 1/24;
        double pnlWRatioG = (double) 1/48;
        double pnlWRatioH = (double) 11/24;
        double pnlWRatioI = (double) 1/12;
        double pnlWRatioJ = (double) 1/4;
        
        // main container
        JPanel pnlMainContainer = new JPanel();
        pnlMainContainer.setLayout(new BoxLayout(pnlMainContainer, BoxLayout.Y_AXIS));
        pnlMainContainer.setPreferredSize(new Dimension(controllerFrame.getWidth(), controllerFrame.getHeight()));
        pnlMainContainer.setMaximumSize(controllerFrame.getPreferredSize());
        pnlMainContainer.setMinimumSize(controllerFrame.getPreferredSize());
        //
        //dialog panel
        JPanel pnlSubContainer4 = new JPanel();
        pnlSubContainer4.setLayout(new BoxLayout(pnlSubContainer4, BoxLayout.X_AXIS));
        pnlSubContainer4.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer4.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer4.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA), (int) (controllerFrame.getHeight() * pnlHRatioC)));
//        pnlSubContainer4.setMaximumSize(pnlSubContainer4.getPreferredSize());
//        pnlSubContainer4.setMinimumSize(pnlSubContainer4.getPreferredSize());
        pnlSubContainer4.setBorder(BorderFactory.createEmptyBorder(0, (int) (controllerFrame.getWidth() * pnlWRatioG)
                , (int) (controllerFrame.getHeight() * pnlHRatioD)
                , (int) (controllerFrame.getWidth() * pnlWRatioG)));
        // dialog box text area
        // Dialog section
        JPanel pnlDialogContainer = dialogPanel(pnlHRatioC, pnlWRatioA);
        pnlSubContainer4.add(pnlDialogContainer);
        pnlMainContainer.add(pnlSubContainer4);
        //
        // Exit Section
        JPanel pnlSubContainer5 = new JPanel();
        pnlSubContainer5.setLayout(new BoxLayout(pnlSubContainer5, BoxLayout.Y_AXIS));
        pnlSubContainer5.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer5.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer5.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA), (int) (controllerFrame.getHeight() * pnlHRatioB)));
        pnlSubContainer5.setMaximumSize(pnlSubContainer5.getPreferredSize());
        pnlSubContainer5.setMinimumSize(pnlSubContainer5.getPreferredSize());
        pnlSubContainer5.setBorder(BorderFactory.createEmptyBorder(0, (int) (controllerFrame.getWidth() * pnlWRatioG)
                , 0
                , (int) (controllerFrame.getWidth() * pnlWRatioG)));
        JButton btnExit = new JButton("Exit");
        btnExit.setLayout(new BoxLayout(btnExit, BoxLayout.X_AXIS));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentY(Component.CENTER_ALIGNMENT);
//        btnExit.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioI), (int) (controllerFrame.getHeight() * pnlHRatioB)));
//        btnExit.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioI), 30));
        btnExit.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioE), 30));
        btnExit.setMaximumSize(btnExit.getPreferredSize());
        btnExit.setMinimumSize(btnExit.getPreferredSize());
//        pnlMainContainer.add(btnExit);
        pnlSubContainer5.add(btnExit);
        pnlMainContainer.add(pnlSubContainer5);
        //
        // complete main container
//        controllerFrame.add(pnlMainContainer);
        controllerFrame.setContentPane(pnlMainContainer);
//        controllerFrame.getContentPane().add(pnlMainContainer);
        controllerFrame.pack();

        controllerFrame.setVisible(true);
        //
        controllerExe server = new controllerExe();
        server.start(4545);
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
        //1. Instantiate controller object
        controllerExe server = new controllerExe();
        //2. create controller frame
        server.createFrame();
        //3. Open controller to connection
        server.start(controllerPortId);
        // set port Id
    }
}