package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final int PORT = 5912;
    static final String ADR = "228.5.6.7";

    static ServerSocket ss = null;
    static Socket socket = null;
    
    static User u = null;
    static message_UI msgUi = null;
    static Sender sender;
    static Receiver receiver ;
    
    
    public static void main(String[] args) throws IOException {        
        ss = new ServerSocket(0);
        sendConnectionDetails(ss.getLocalPort(), ss.getInetAddress().getHostName());
        socket = ss.accept();
        System.out.println("Connected!");
        sender = new Sender();
        Thread sT = new Thread(sender);
        sT.start();
        receiver = new Receiver();
        Thread rT = new Thread(receiver);
        rT.start();
    }
    
    static void sendConnectionDetails(int port, String hostname) throws IOException{
        try {
            InetAddress multicastGroup = null;
            DatagramSocket datagramSocket = new DatagramSocket();
            multicastGroup = InetAddress.getByName(ADR);
            String msg = u.getName() + "," + u.getEmail() + "," + port + "," + hostname;
            byte[] data = msg.getBytes();
            DatagramPacket datagram = new DatagramPacket(data, data.length);
            datagram.setAddress(multicastGroup);
            datagram.setPort(PORT);            
            datagramSocket.send(datagram);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

}
