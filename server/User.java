/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
    
/**
 *
 * @author Mohsin Hayat
 */
public class User extends Thread{ //User Thread class.

    String name;
    String email;
    String ip;
    int port;
    Socket socket = null; //connected socket has to be saved
    private DataInputStream in =  null; 
    private DataOutputStream out     = null; 
    private volatile boolean shouldRun = true;
    
    @Override
    public void run(){
        while(shouldRun){
            try {
                String msg;
                msg = in.readUTF();
                
                if(msg.equals("/disconnected")){
                    Server.removeUser(this);
                    break;
                }
                
                System.out.println(this.name + ": " + msg);
                Server.sendToAll(this.name + ": " + msg);
                
            } catch (IOException ex) {
                System.out.println(this.getName() + " disconnected."); //when an exception is found, the user is immediately disconnected.
                try {
                    Server.removeUser(this);
                } catch (IOException ex1) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex1);
                }
                break;
            }
            
        }
    }
    
    public User(String name, String email, String ip, int port) {
        this.name = name;
        this.email = email;
        this.ip = ip;
        this.port = port;

        try {
            socket = new Socket(this.ip, this.port);
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
            out = new DataOutputStream(socket.getOutputStream()); 
        } catch (IOException ex) {
            
        }
        
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getUName() {
        return name;
    }

    public void setUName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    void sendMsg(String msg) throws IOException{
        try{
            out.writeUTF(msg); //sending message to the connected socket.
        }catch (IOException ex) {
                System.out.println(this.getName() + " disconnected.");
                Server.removeUser(this);
        }
    }
    
     public void stopUser() throws IOException {
        shouldRun = false;
        in.close();
        out.close();
        socket.close();
    }
    
    
}
