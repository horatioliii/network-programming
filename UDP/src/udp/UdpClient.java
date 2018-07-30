package udp;

import java.net.*;

public class UdpClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket(0);

            byte[] sendData; // Store outgoing data
            byte[] receiveData = new byte[1024]; // Store incoming data

            InetAddress serverAddress = InetAddress.getByName("localhost");

            clientSocket.setSoTimeout(3000);
            String stringSendData = "Hello server";
            sendData = stringSendData.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9090);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            receiveData = receivePacket.getData();
            String stringReceiveDate = new String(receiveData);
            System.out.println("From server: " + stringReceiveDate);
            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
