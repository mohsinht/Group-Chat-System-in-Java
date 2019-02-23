
package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Receiver extends Thread {
    private DataInputStream in =  null; 
    String msg = "";
    private volatile boolean shouldRun = true;
    @Override
    public void run(){
        while(shouldRun){
            try {
                msg = in.readUTF();
                System.out.println(msg);
                Client.msgUi.updateMsg(msg);
            } catch (IOException ex) {
                try {
                    System.out.println("You left.");
//                    stopReceiver();
//                   Sender s = new Sender();
//                   s.stopSender();
                 //  Client.ss.close();
                    break;
                } catch (Exception ex1) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
    
    public Receiver() throws IOException{
        in = new DataInputStream( 
                new BufferedInputStream(Client.socket.getInputStream())); 
    }
    
    
      public void stopReceiver() throws IOException {
        shouldRun = false;
        in.close();
       
        
       
    }
}
