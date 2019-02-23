package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin extends Thread {

    private volatile boolean shouldRun = true;
    ServerSocket server;

    @Override
    public void run() {
        try {
            server = new ServerSocket(9090); //creating a server at http://localhost:9090/

            while (shouldRun) {

                try (Socket socket = server.accept()) {
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";
                    httpResponse += "Last 10 Messages: \n\n";
                    int s = 0;

                    for (int i = Server.MSGs.start(); s < 10; i++) {
                        s++;
                        httpResponse += Server.MSGs.getMsg(i) + "\n";  //Writing messages to the webpage
                    }

                    socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }catch(Exception e){
                   
                }
            }

            System.out.println("Admin is off");
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Admin() {
    }

    public void stopRegistrar() throws IOException {

        shouldRun = false;
        
            server.close();
       
       

    }

}
