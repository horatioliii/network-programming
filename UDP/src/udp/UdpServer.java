package udp;

import java.net.*;

public class UdpServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9090);

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String stringReceived = new String(receivePacket.getData());
                System.out.println("Received " + stringReceived);

                String stringData = "Hello client";
                sendData = stringData.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
