package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender extends Thread {

    public Sender() {
    }

    private DataOutputStream out = null;
    private volatile boolean shouldRun = true;

    public void run() {
        try {
            Scanner in = new Scanner(System.in);
            String msg = "";
            out = new DataOutputStream(Client.socket.getOutputStream());
            while (this.shouldRun) {
                msg = in.nextLine();
                out.writeUTF(msg);
            }
            
            System.out.println("sender is off");
            
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMsg(String msg) {
        try {
            out = new DataOutputStream(Client.socket.getOutputStream());
            out.writeUTF(msg);
        } catch (IOException ex) {
            System.out.println("problem connecting to the server.");
        }
    }

    public void stopSender() throws IOException {
        sendMsg("/disconnected");
        this.shouldRun = false;
        out.close();
        System.out.println("sender closed");
    }

}
