package socket;
import java.io.IOException;
import java.net.*;

public class LocalPortScanner {
    public static void main(String[] args) {
        int port = 1;
        while (port <= 65535) {
            try {
                ServerSocket server = new ServerSocket(port);
                System.out.println(port + "is not Open.");
            } catch (IOException e) {
                System.out.println(port + " is open!");
            }
            port++;
        }
    }
}
