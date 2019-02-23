package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registrar implements Runnable { //Thread that waits for users to connect to the group.

    MulticastSocket socket;
    InetAddress multicastGroup;
    private volatile boolean shouldRun = true;

    @Override
    public void run() {

        try {
            multicastGroup = InetAddress.getByName(Server.ADR);
            socket = new MulticastSocket(Server.PORT);
            socket.joinGroup(multicastGroup);

            while (shouldRun) {
                System.out.println("Registrar Waiting!");
                byte[] data = new byte[1000];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                try {
                    socket.receive(packet);
                } catch (IOException ex) {
                }

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(message);

                if (!message.isEmpty()) {
                    String[] arr = message.split(","); //whenever a request is received, a username and email is sent with it. 
                    Server.addUser(arr[0], arr[1], arr[3], Integer.parseInt(arr[2])); //registering user to the group.
                }
            }
            System.out.println("registrar is off");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Registrar() {
    }

    //function to stop the writer thread.it is called from main method
    public void stopRegistrar() throws IOException {
        shouldRun = false;
        socket.leaveGroup(multicastGroup);
        socket.close();
    }

}
