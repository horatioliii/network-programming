package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ping {

    public static void main(String[] args) {
        System.out.println("Testing unreachable IpAddress:");
        pingIpAddress("123.123.123.123");
        System.out.println("Testing reachable IpAddress:");
        pingIpAddress("google.com");
    }

    private static void pingIpAddress(String ipAddress) {
        try {
            InetAddress hostInetAddress = InetAddress.getByName(ipAddress);
            System.out.println(hostInetAddress.isReachable(1000));
            Process process = Runtime.getRuntime().exec("ping " + hostInetAddress.getHostName());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String commandOutput = "";
            boolean isReachable = true;
            while ((commandOutput = bufferedReader.readLine()) != null) {
                if (commandOutput.contains("Request timeout")) {
                    isReachable = false;
                    break;
                }
                System.out.println(commandOutput);
            }
            if (isReachable) {
                System.out.println("Host is reachable.");
            } else {
                System.out.println("Host is unreachable.");
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ping.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ping.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
