import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class controllerExe {
    // initialized variables
    public JTextField ipString;
    public JTextField portString;
    public JTextField initTimeout;
    public JTextField timeoutDelay;
    public JTextField ctrlName;
    String[][] midReplyCmds;
    public JComboBox<String[]> midCmdCB;
    public String midString;
    public JTextField midInputString;
    public JTextField serializedMidString;
    public JButton btnOpen;
    public JButton btnSendMid;
    public JButton btnSendSerializedMid;
    public JTextArea dialogBox;
    JButton btnAutoMidMode;
    openProtocolClientParserMIDClass oPCP = new openProtocolClientParserMIDClass();
    openProtocolClientSerializerMIDClass oPCS = new openProtocolClientSerializerMIDClass();
    // Controller Frame
    JFrame controllerFrame = new JFrame("Controller");
    // ServerSocket & Client Socket
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    public DataOutputStream os;
    public InputStreamReader input;
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
    public void acceptCall() {
        try {
            clientSocket = serverSocket.accept();
            int checkPeriod = (int) Float.parseFloat(timeoutDelay.getText());
            clientSocket.setSoTimeout(checkPeriod);
            clientSocket.setSoLinger(true, 1);
        } catch (Throwable e) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
            System.out.println("Something went wrong in acceptCall\n" + e.getMessage() + "\n @ " + jsonTimestamp);
            e.printStackTrace();
        }
//        return cSckt;
    }
    //
    public void stop() {
        try {
            os.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    public String sendMessage(String msg) {
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
    byte[] msgBytes(String serializedMsg) {
        return serializedMsg.getBytes(StandardCharsets.US_ASCII);
    }
    //
    public String readFully(InputStreamReader in) throws IOException {
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
    public JPanel connectPanel(double pnlWRatio1)  {
        // Connection JPanels
        JPanel pnlCnctMain = new JPanel();
        pnlCnctMain.setLayout(new BoxLayout(pnlCnctMain, BoxLayout.Y_AXIS));
        pnlCnctMain.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCnctMain.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Connection Header
        JLabel lblManualConnect = new JLabel("Connection");
        lblManualConnect.setFont(new Font("Courier", Font.BOLD, 16));
//        lblManualConnect.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblManualConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblManualConnect.setAlignmentY(Component.TOP_ALIGNMENT);
        lblManualConnect.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlCnctMain.add(lblManualConnect);
        // Connect Detail
        JPanel pnlCnctDtl = new JPanel();
        pnlCnctDtl.setLayout(new BoxLayout(pnlCnctDtl, BoxLayout.X_AXIS));
//        pnlCnctDtl.setAlignmentX(Component.LEFT_ALIGNMENT);
        // labels
        JPanel pnlCnctLbls = new JPanel();
        pnlCnctLbls.setPreferredSize(new Dimension(85, 70));
//        pnlCnctLbls.setMaximumSize(new Dimension(85, 70));
//        pnlCnctLbls.setMinimumSize(new Dimension(50, 70));
        pnlCnctLbls.setMaximumSize(pnlCnctLbls.getPreferredSize());
//        pnlCnctLbls.setMinimumSize(pnlCnctLbls.getPreferredSize());
        pnlCnctLbls.setLayout(new BoxLayout(pnlCnctLbls, BoxLayout.Y_AXIS));
        pnlCnctLbls.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlCnctipLbl = new JPanel();
        pnlCnctipLbl.setMaximumSize(new Dimension(85, 30));
//        pnlCnctipLbl.setMinimumSize(new Dimension(50, 30));
        pnlCnctipLbl.setLayout(new BoxLayout(pnlCnctipLbl, BoxLayout.X_AXIS));
        JLabel ipLbl = new JLabel("Host:");
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
//        pnlCnctDtlFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioH), 70));
//        pnlCnctDtlFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 70));
//        pnlCnctDtlFlds.setPreferredSize(new Dimension(pnlSubContainer1a.getWidth(), 70));
//        pnlCnctDtlFlds.setMaximumSize(pnlCnctDtlFlds.getPreferredSize());
//        pnlCnctDtlFlds.setMinimumSize(pnlCnctDtlFlds.getPreferredSize());
        pnlCnctDtlFlds.setLayout(new BoxLayout(pnlCnctDtlFlds, BoxLayout.Y_AXIS));
        pnlCnctDtlFlds.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlCnctDtlFlds.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlCnctDtlIpPortFlds = new JPanel();
//        pnlCnctDtlIpPortFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioH), 30));
        pnlCnctDtlIpPortFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 30));
        pnlCnctDtlIpPortFlds.setMaximumSize(pnlCnctDtlIpPortFlds.getPreferredSize());
//        pnlCnctDtlIpPortFlds.setMinimumSize(pnlCnctDtlIpPortFlds.getPreferredSize());
        pnlCnctDtlIpPortFlds.setLayout(new BoxLayout(pnlCnctDtlIpPortFlds, BoxLayout.X_AXIS));
        ipString = new JTextField("127.0.0.1");
        ipString.setToolTipText("Device/Controller IP Address required.");
        ipString.setAlignmentX(Component.LEFT_ALIGNMENT);
//        ipString.setAlignmentY(Component.TOP_ALIGNMENT);
//        ipString.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
        ipString.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlCnctDtlIpPortFlds.add(ipString);
        JPanel pnlCnctDtlPortFldsLbl = new JPanel();
        pnlCnctDtlPortFldsLbl.setPreferredSize(new Dimension(65, 30));
        pnlCnctDtlPortFldsLbl.setMaximumSize(pnlCnctDtlPortFldsLbl.getPreferredSize());
//        pnlCnctDtlPortFldsLbl.setMinimumSize(pnlCnctDtlPortFldsLbl.getPreferredSize());
        JLabel portLbl = new JLabel("Port: ");
        portLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
//        portLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
//        portLbl.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 35));
        pnlCnctDtlPortFldsLbl.add(portLbl);
//        pnlCnctDtlIpPortFlds.add(portLbl);
        pnlCnctDtlIpPortFlds.add(pnlCnctDtlPortFldsLbl);
        portString = new JTextField("4545");
        portString.setToolTipText("Device/Controller Port Id required.");
        portString.setAlignmentX(Component.LEFT_ALIGNMENT);
//        portString.setAlignmentY(Component.TOP_ALIGNMENT);
//        portString.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
        portString.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlCnctDtlIpPortFlds.add(portString);
        pnlCnctDtlFlds.add(pnlCnctDtlIpPortFlds);
        JPanel pnlCnctDtlFldspd = new JPanel();
//        pnlCnctDtlFldspd.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioH), 5));
        pnlCnctDtlFldspd.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 5));
        pnlCnctDtlFldspd.setMaximumSize(pnlCnctDtlFldspd.getPreferredSize());
//        pnlCnctDtlFldspd.setMinimumSize(pnlCnctDtlFldspd.getPreferredSize());
        pnlCnctDtlFlds.add(pnlCnctDtlFldspd);
        JPanel pnlCnctDtlctrlNameFlds = new JPanel();
//        pnlCnctDtlctrlNameFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioH), 30));
        pnlCnctDtlctrlNameFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio1), 30));
        pnlCnctDtlctrlNameFlds.setMaximumSize(pnlCnctDtlctrlNameFlds.getPreferredSize());
//        pnlCnctDtlctrlNameFlds.setMinimumSize(pnlCnctDtlctrlNameFlds.getPreferredSize());
        pnlCnctDtlctrlNameFlds.setLayout(new BoxLayout(pnlCnctDtlctrlNameFlds, BoxLayout.X_AXIS));
        ctrlName = new JTextField("deviceName");
        ctrlName.setToolTipText("Device/Controller Name.");
        ctrlName.setAlignmentX(Component.LEFT_ALIGNMENT);
//        ctrlName.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//        ctrlName.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
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
    public JPanel midPanel(double pnlWRatio2) {
        // MID section
        JPanel pnlMidContainer = new JPanel();
        pnlMidContainer.setLayout(new BoxLayout(pnlMidContainer, BoxLayout.Y_AXIS));
        pnlMidContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlMidContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
        // MID Header
        JLabel lblMessages = new JLabel("MID Messages");
        lblMessages.setFont(new Font("Courier", Font.BOLD, 14));
//        lblMessages.setLayout(new BoxLayout(lblMessages, BoxLayout.X_AXIS));
        lblMessages.setAlignmentY(Component.TOP_ALIGNMENT);
        lblMessages.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMessages.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlMidContainer.add(lblMessages);
        // mid command container
        JPanel pnlMidCommand = new JPanel();
        pnlMidCommand.setLayout(new BoxLayout(pnlMidCommand, BoxLayout.X_AXIS));
        pnlMidCommand.setAlignmentX(Component.LEFT_ALIGNMENT);
        // labels
        JPanel pnlAllMIDLbls = new JPanel();
        pnlAllMIDLbls.setPreferredSize(new Dimension(150, 70));
//        pnlAllMIDLbls.setMaximumSize(new Dimension(200, 70));
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
//        pnlMIDDtlFlds.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlMIDDtlFlds.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel pnlMIDDtlCmdInVFlds = new JPanel();
        pnlMIDDtlCmdInVFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 30));
        pnlMIDDtlCmdInVFlds.setMaximumSize(pnlMIDDtlCmdInVFlds.getPreferredSize());
//        pnlMIDDtlCmdInVFlds.setMinimumSize(pnlMIDDtlCmdInVFlds.getPreferredSize());
        pnlMIDDtlCmdInVFlds.setLayout(new BoxLayout(pnlMIDDtlCmdInVFlds, BoxLayout.X_AXIS));
        // call method to get list of commands as dropdown.
        String[][] midCmdDd = relpyCmdList();
//        ComboItem[] items = new ComboItem[midCmdDd.length];
//        for (int i = 0; i < midCmdDd.length; i++) {
//            items[i] = new ComboItem(midCmdDd[i], midCmdDd[i]);
//        }
        midCmdCB = new JComboBox<>(midCmdDd);
        midCmdCB.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        midCmdCB.setBorder(null);
        midCmdCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        midCmdCB.setToolTipText("'MID' Command");
        midCmdCB.setPreferredSize(new Dimension(125, 30));
        midCmdCB.setMaximumSize(midCmdCB.getPreferredSize());
        midCmdCB.setMinimumSize(midCmdCB.getPreferredSize());
        midCmdCB.setBackground(Color.white);

        pnlMIDDtlCmdInVFlds.add(midCmdCB);
        JPanel pnlMIDDtlInFlds = new JPanel();
        pnlMIDDtlInFlds.setPreferredSize(new Dimension(110, 30));
        pnlMIDDtlInFlds.setMaximumSize(pnlMIDDtlInFlds.getPreferredSize());
        JLabel lblVals = new JLabel("Input Values:");
        lblVals.setAlignmentX(Component.CENTER_ALIGNMENT);
//        pnlMIDDtlCmdInVFlds.add(lblVals);
        pnlMIDDtlInFlds.add(lblVals);
        pnlMIDDtlCmdInVFlds.add(pnlMIDDtlInFlds);
//        JTextField midInputString = new JTextField();
        midInputString = new JTextField();
        midInputString.setToolTipText("include MID command values if applicable.");
        midInputString.setAlignmentX(Component.LEFT_ALIGNMENT);
//        midInputString.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
        midInputString.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//        midInputString.add(midCmdCB);
        pnlMIDDtlCmdInVFlds.add(midInputString);
        pnlMIDDtlFlds.add(pnlMIDDtlCmdInVFlds);
        JPanel pnlMIDDtlFldsPd = new JPanel();
        pnlMIDDtlFldsPd.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 5));
        pnlMIDDtlFldsPd.setMaximumSize(pnlMIDDtlFldsPd.getPreferredSize());
        pnlMIDDtlFlds.add(pnlMIDDtlFldsPd);
        JPanel pnlMIDDtlSrlzdFlds = new JPanel();
        pnlMIDDtlSrlzdFlds.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatio2), 30));
        pnlMIDDtlSrlzdFlds.setMaximumSize(pnlMIDDtlSrlzdFlds.getPreferredSize());
//        pnlMIDDtlSrlzdFlds.setMinimumSize(pnlMIDDtlSrlzdFlds.getPreferredSize());
        pnlMIDDtlSrlzdFlds.setLayout(new BoxLayout(pnlMIDDtlSrlzdFlds, BoxLayout.X_AXIS));
//        JTextField serializedMidString = new JTextField();
        serializedMidString = new JTextField();
        serializedMidString.setToolTipText("include serialized MID message.");
        serializedMidString.setAlignmentX(Component.LEFT_ALIGNMENT);
//        serializedMidString.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 10));
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
//        pnlSendMID.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlSendMID.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Connection Header
        JLabel blankLabel = new JLabel(" ");
        blankLabel.setFont(new Font("Courier", Font.BOLD, 14));
//        blankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blankLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        blankLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        blankLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlSendMID.add(blankLabel);
        // main mid container
//        JButton btnSendMid = new JButton("Send");
        btnSendMid = new JButton("send");
//        btnSendMid.setAlignmentY(Component.TOP_ALIGNMENT);
//        btnSendMid.setAlignmentY(Component.CENTER_ALIGNMENT);
//        btnSendMid.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSendMid.setPreferredSize(new Dimension(100, 30));
        btnSendMid.setMaximumSize(btnSendMid.getPreferredSize());
        btnSendMid.setMinimumSize(btnSendMid.getPreferredSize());
//        btnSendMid.setMaximumSize(new Dimension(125, 30));
//        btnSendMid.setMinimumSize(new Dimension(125, 30));
        btnSendMid.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSendMID.add(btnSendMid);
        JPanel pnlSendMIDPd = new JPanel();
        pnlSendMIDPd.setPreferredSize(new Dimension(100, 5));
//        pnlSendMIDPd.setMaximumSize(new Dimension(125, 5));
//        pnlSendMIDPd.setMinimumSize(new Dimension(125, 5));
        pnlSendMIDPd.setMaximumSize(pnlSendMIDPd.getPreferredSize());
        pnlSendMIDPd.setMinimumSize(pnlSendMIDPd.getPreferredSize());
//        pnlSendMIDPd.setPreferredSize(new Dimension(-1, 5));
        pnlSendMID.add(pnlSendMIDPd);
//        JButton btnSendSerializedMid = new JButton("Send");
        btnSendSerializedMid = new JButton("send");
//        btnSendSerializedMid.setAlignmentY(Component.TOP_ALIGNMENT);
//        btnSendSerializedMid.setAlignmentY(Component.CENTER_ALIGNMENT);
//        btnSendSerializedMid.setPreferredSize(new Dimension(pnlSendMID.getWidth(), 45));
        btnSendSerializedMid.setPreferredSize(new Dimension(100, 30));
        btnSendSerializedMid.setMaximumSize(btnSendSerializedMid.getPreferredSize());
        btnSendSerializedMid.setMinimumSize(btnSendSerializedMid.getPreferredSize());
        btnSendSerializedMid.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSendMID.add(btnSendSerializedMid);
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
//        lblDialog.setAlignmentX(Component.RIGHT_ALIGNMENT);
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
    public void createFrame() throws IOException {
        controllerMessaging ctrlMsgr = new controllerMessaging();
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
                controllerLogger.controllerLog("exit", "closing window", "info");
                try {
                    controllerLogger.controllerLog("exit", "closing window", "info");
//                    ctrlMsgr.stop(serverSocket);
                    int inputPortId = Integer.parseInt(portString.getText());
                    ctrlMsgr.stop(serverSocket, inputPortId, dialogBox);
                } catch (Throwable a) {
                    controllerLogger.controllerLog("exit", a.getMessage(), "error");
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

        // JPanels containers
        JPanel pnlSubContainer1 = new JPanel();
        pnlSubContainer1.setLayout(new BoxLayout(pnlSubContainer1, BoxLayout.X_AXIS));
        pnlSubContainer1.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer1.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA)
                , (int) (controllerFrame.getHeight() * pnlHRatioA)));
        pnlSubContainer1.setBorder(BorderFactory.createEmptyBorder(0, (int) (controllerFrame.getWidth() * pnlWRatioG)
                , 0, (int) (controllerFrame.getWidth() * pnlWRatioG)));
        //
        JPanel pnlSubContainer1a = new JPanel();
        pnlSubContainer1a.setLayout(new BoxLayout(pnlSubContainer1a, BoxLayout.X_AXIS));
        pnlSubContainer1a.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer1a.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer1a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0
                ,  (int) (controllerFrame.getWidth() * pnlWRatioG)));
        // Connection JPanels
        JPanel pnlCnctMain = connectPanel(pnlWRatioF);
        // ip/port JPanels
        pnlSubContainer1a.add(pnlCnctMain);
        pnlSubContainer1.add(pnlSubContainer1a);
        //
        JPanel pnlSubContainer1b = new JPanel();
        pnlSubContainer1b.setLayout(new BoxLayout(pnlSubContainer1b, BoxLayout.X_AXIS));
        pnlSubContainer1b.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer1b.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer1b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // Timing JPanels
        JPanel pnlTmMain = timingPanel();
        pnlSubContainer1b.add(pnlTmMain);
        pnlSubContainer1.add(pnlSubContainer1b);
        //
        pnlMainContainer.add(pnlSubContainer1);
        //
        // connection initiator
        JPanel pnlSubContainer2 = new JPanel();
        pnlSubContainer2.setLayout(new BoxLayout(pnlSubContainer2, BoxLayout.X_AXIS));
        pnlSubContainer2.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA)
                , (int) (controllerFrame.getHeight() * pnlHRatioB)));
        pnlSubContainer2.setMaximumSize(pnlSubContainer2.getPreferredSize());
        pnlSubContainer2.setMinimumSize(pnlSubContainer2.getPreferredSize());
        pnlSubContainer2.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer2.setBorder(BorderFactory.createEmptyBorder(0, (int) (controllerFrame.getWidth() * pnlWRatioG)
                , 0
                , (int) (controllerFrame.getWidth() * pnlWRatioG)));
        // button
        btnOpen = new JButton("open connection");
        btnOpen.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnOpen.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioD), 30));
        btnOpen.setMaximumSize(btnOpen.getPreferredSize());
        btnOpen.setMinimumSize(btnOpen.getPreferredSize());
        pnlSubContainer2.add(btnOpen);
        pnlMainContainer.add(pnlSubContainer2);
        //
        JPanel pnlSubContainer3 = new JPanel();
        pnlSubContainer3.setLayout(new BoxLayout(pnlSubContainer3, BoxLayout.X_AXIS));
        pnlSubContainer3.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA)
                , (int) (controllerFrame.getHeight() * pnlHRatioA)));
        pnlSubContainer3.setBorder(BorderFactory.createEmptyBorder(0, (int) (controllerFrame.getWidth() * pnlWRatioG)
                , 0, (int) (controllerFrame.getWidth() * pnlWRatioG)));
        JPanel pnlSubContainer3a = new JPanel();
        pnlSubContainer3a.setLayout(new BoxLayout(pnlSubContainer3a, BoxLayout.X_AXIS));
        pnlSubContainer3a.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer3a.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer3a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0
                ,  (int) (controllerFrame.getWidth() * pnlWRatioG)));
        JPanel pnlMidContainer = midPanel(pnlWRatioF);
        pnlSubContainer3a.add(pnlMidContainer);
        pnlSubContainer3.add(pnlSubContainer3a);
        //
        JPanel pnlSubContainer3b = new JPanel();
        pnlSubContainer3b.setLayout(new BoxLayout(pnlSubContainer3b, BoxLayout.X_AXIS));
        pnlSubContainer3b.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer3b.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer3b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // Send JPanels
        JPanel pnlSend = sendPanel();
        pnlSubContainer3b.add(pnlSend);
        pnlSubContainer3.add(pnlSubContainer3b);
        pnlMainContainer.add(pnlSubContainer3);
        //
        //dialog panel
        JPanel pnlSubContainer4 = new JPanel();
        pnlSubContainer4.setLayout(new BoxLayout(pnlSubContainer4, BoxLayout.X_AXIS));
        pnlSubContainer4.setAlignmentY(Component.CENTER_ALIGNMENT);
        pnlSubContainer4.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSubContainer4.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioA), (int) (controllerFrame.getHeight() * pnlHRatioC)));
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
        btnExit.setPreferredSize(new Dimension((int) (controllerFrame.getWidth() * pnlWRatioE), 30));
        btnExit.setMaximumSize(btnExit.getPreferredSize());
        btnExit.setMinimumSize(btnExit.getPreferredSize());
        pnlSubContainer5.add(btnExit);
        pnlMainContainer.add(pnlSubContainer5);
        //
        // complete main container
//        controllerFrame.add(pnlMainContainer);
        controllerFrame.setContentPane(pnlMainContainer);
//        controllerFrame.getContentPane().add(pnlMainContainer);
        controllerFrame.pack();

        controllerFrame.setTitle("Controller");
        controllerFrame.setVisible(true);

        //

        JTextField finalIpString = ipString;
        JTextField finalPortString = portString;
        JTextField finalInitTimeout = initTimeout;
        JTextField finalTimeoutDelay = timeoutDelay;

//        //3. Open controller to connection
//        server.start(controllerPortId);
        btnOpen.addActionListener(a -> {
            //
            try {
                String inputIdAddress = finalIpString.getText();
                int inputPortId = Integer.parseInt(finalPortString.getText());
                int initDelay = (int) Float.parseFloat(finalInitTimeout.getText());
                int checkPeriod = (int) Float.parseFloat(finalTimeoutDelay.getText());

                if ("open connection".equals(btnOpen.getText())) {
                    serverSocket = new ServerSocket(inputPortId);
                    System.out.println("server socket checks: ");
                    System.out.println("1-" + serverSocket.getLocalSocketAddress());
                    System.out.println("2-" + serverSocket.getLocalSocketAddress().toString());
                    System.out.println("3-" + serverSocket.getInetAddress());
                    System.out.println("4-" + serverSocket.getInetAddress().getCanonicalHostName());
                    System.out.println("5-" + serverSocket.getInetAddress().getHostAddress());
                    System.out.println("6-" + Arrays.toString(serverSocket.getInetAddress().getAddress()));
                    System.out.println("7-" + serverSocket.getInetAddress().getHostName());
                    serverSocket.setSoTimeout(initDelay);
//                    while (true) {
                    // 1.a. accept call?
//                    clientSocket = (Socket) acceptCall();
                    acceptCall();
                    System.out.println("client socket checks: ");
                    System.out.println("1-" + clientSocket.getRemoteSocketAddress());
                    System.out.println("2-" + clientSocket.getRemoteSocketAddress().toString());
                    System.out.println("3-" + clientSocket.getOutputStream());
                    System.out.println("4-" + clientSocket.getOutputStream().toString());
                    System.out.println("5-" + clientSocket.getLocalAddress());
                    System.out.println("6-" + clientSocket.getLocalAddress().getHostName());
                    System.out.println("7-" + clientSocket.getLocalAddress().getHostAddress());
                    System.out.println("8-" + Arrays.toString(clientSocket.getLocalAddress().getAddress()));
                    System.out.println("9-" + clientSocket.getLocalAddress().getCanonicalHostName());
                    System.out.println("10-" + clientSocket.getInetAddress());
                    System.out.println("11-" + clientSocket.getInetAddress().getCanonicalHostName());
                    System.out.println("12-" + clientSocket.getInetAddress().getHostAddress());
                    System.out.println("13-" + Arrays.toString(clientSocket.getInetAddress().getAddress()));
                    System.out.println("14-" + clientSocket.getInetAddress().getHostName());
                    System.out.println("15-" + clientSocket.getLocalSocketAddress());
                    System.out.println("client socket checks: ");
                    //append & log the connection
                    appendDialog(dialogBox, "Connection open\nListening on: " + inputPortId);
                    //
                    String startMid = "MID0002";
                    //append & log the input
                    appendDialog(dialogBox, "controller message: \n" + startMid);
                    controllerLogger.controllerLog("controller2Host", "controller message: " + startMid, "info");

                    //append & log the input
                    String controllerMsg = oPCS.integratorString(startMid);
                    appendDialog(dialogBox, "controller message serialized: \n" + controllerMsg);
                    controllerLogger.controllerLog("controller2Host", "host message serialized: " + controllerMsg, "info");

                    //update button press
                    btnOpen.setText("close connection");

                    String hostResponse = sendMessage(controllerMsg);
                    appendDialog(dialogBox, "controller message serialized: \n" + hostResponse);
                    controllerLogger.controllerLog("controller2Host", "controller message serialized: " + hostResponse, "info");

                    Object hostResponseParsed = oPCP.parseMessage(hostResponse);
                    appendDialog(dialogBox, "controller message parsed: \n" + hostResponseParsed);
                    controllerLogger.controllerLog("controller2Host", "controller message parsed: " + hostResponseParsed, "info");
//                    }
                } else {
                    // close connection
                    stop();
                    //update button press
                    btnOpen.setText("open connection");
                }

            } catch (Throwable o) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                //
                String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                controllerLogger.controllerLog("controller2Host", "IO Exception in connection button\n" + o.getMessage() + "\n @ " + jsonTimestamp, "error");
            }
        });
        btnSendMid.addActionListener(b -> {
            //
            try {
                String midCommand = null;
//                if (midCmdCB.getItemAt(0).isEmpty()) {
//                    midCommand = midString;
//                } else {
//                    midCommand = midCmdCB.getEditor().getItem().toString();
//                }
                System.out.println("the combobox input was : " + midCommand);
                String midValues = midInputString.getText();
                //append & log the input
                appendDialog(dialogBox, "controller message: \n" + midCommand + " " + midValues);
                controllerLogger.controllerLog("controller2Host", "controller message: " + midCommand + " " + midValues, "info");

                //append & log the input
                String integratorMsg = oPCS.integratorString(midCommand + " " + midValues);
                appendDialog(dialogBox, "controller message serialized: \n" + integratorMsg);
                controllerLogger.controllerLog("controller2Host", "controller message serialized: " + integratorMsg, "info");

                String hostResponse = sendMessage(integratorMsg);
                appendDialog(dialogBox, "controller message serialized: \n" + hostResponse);
                controllerLogger.controllerLog("controller2Host", "controller message serialized: " + hostResponse, "info");

                Object hostResponseParsed = oPCP.parseMessage(hostResponse);
                appendDialog(dialogBox, "controller message parsed: \n" + hostResponseParsed);
                controllerLogger.controllerLog("controller2Host", "controller message parsed: " + hostResponseParsed, "info");

            } catch (Throwable o) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                //
                String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                controllerLogger.controllerLog("controller2Host", "IO Exception in mid send button\n" + o.getMessage() + "\n @ " + jsonTimestamp, "error");
            }
        });
        btnSendSerializedMid.addActionListener(c -> {
            //
            try {
                String serializedMidCommand = serializedMidString.getText();
                //append & log the input
                appendDialog(dialogBox, "host message: \n" + serializedMidCommand);
                controllerLogger.controllerLog("controller2Host", "host message: " + serializedMidCommand, "info");
                // check last two characters
                int msgLength = serializedMidCommand.length();
                String lastTwoChars = serializedMidCommand.substring(Math.max(msgLength - 2, 0));
                System.out.println("last two chars were? " + lastTwoChars);
                if ("\0".equals(lastTwoChars)) {
                    String hostResponse = sendMessage(serializedMidCommand);
                    appendDialog(dialogBox, "controller message serialized: \n" + hostResponse);
                    controllerLogger.controllerLog("controller2Host", "controller message serialized: " + hostResponse, "info");

                    Object hostResponseParsed = oPCP.parseMessage(hostResponse);
                    appendDialog(dialogBox, "controller message parsed: \n" + hostResponseParsed);
                    controllerLogger.controllerLog("controller2Host", "controller message parsed: " + hostResponseParsed, "info");
                } else {
                    String hostResponse = sendMessage(serializedMidCommand + "\0");
                    appendDialog(dialogBox, "controller message serialized: \n" + hostResponse);
                    controllerLogger.controllerLog("controller2Host", "controller message serialized: " + hostResponse, "info");

                    Object hostResponseParsed = oPCP.parseMessage(hostResponse);
                    appendDialog(dialogBox, "controller message parsed: \n" + hostResponseParsed);
                    controllerLogger.controllerLog("controller2Host", "controller message parsed: " + hostResponseParsed, "info");
                }
            } catch (Throwable o) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now();
                //
                String jsonTimestamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx"));
                controllerLogger.controllerLog("controller2Host", "IO Exception in serialized mid send button\n" + o.getMessage() + "\n @ " + jsonTimestamp, "error");
                }
        });
        //
        btnExit.addActionListener(e -> {
            try {
                controllerLogger.controllerLog("exit", "exit button", "info");
                // close connection
                stop();
            } catch (Throwable b) {
                controllerLogger.controllerLog("exit", "Exit Button, can't stop connection\n" + b.getMessage(), "error");
            }
            System.exit(0);
        });
    }
    //
    void appendDialog(JTextArea tArea, String dialog){
        tArea.append(dialog);
        tArea.append("\n");
    }
    //
    public static void main(String[] args) throws IOException {
        //1. Instantiate controller object
        controllerExe server = new controllerExe();
        //2. create controller frame
        server.createFrame();
        // set port Id
    }
}