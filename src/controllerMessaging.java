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
    ServerSocket serverSocket;
    DataOutputStream os;
    InputStreamReader inputStrRdr;
    InputStreamReader input;

    void openConnection(int port) {
        try {
            // 1.a. create server
            serverSocket = new ServerSocket(port);
            // 1.b. accept call?
            Socket clientSocket = (Socket) acceptCall(serverSocket);
            // 2. create objects for
            input = new InputStreamReader(clientSocket.getInputStream());
            os = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            hostLogger.hostLog("startConnection", e.getMessage(), "error");
            System.out.println("Something went wrong in startConnection Or keepAlive\n" + e.getMessage());
            e.printStackTrace();
            //        }
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
    //
    byte[] msgBytes(String serializedMsg) {
        return serializedMsg.getBytes(StandardCharsets.US_ASCII);
    }
    //
    void stop(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
