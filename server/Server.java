package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static final int PORT = 5912;
    static final String ADR = "228.5.6.7";
    public static ArrayList<User> users = new ArrayList<>();
    ;
    public static messages MSGs = new messages();
    static Thread regT;
    static Admin admin;
    User user;
    static Registrar registrar;

    public static void main(String[] args) {

        registrar = new Registrar();
        regT = new Thread(registrar); //Registration thread waiting for new connections
        regT.start();
        admin = new Admin();
        Thread admT = new Thread(admin); //Admin thread writing 10 messages on the webpage: http://localhost:9090/
        admT.start();

        try {
            listenForInput();

        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addUser(String name, String email, String ip, int port) throws IOException { //adding user to the group
        User u = new User(name, email, ip, port);
        Thread uT = new Thread(u);
        Server.users.add(u);
        uT.start(); //each user has a thread.
        Server.sendToAll(name + " has connected.");

    }

    static void sendToAll(String msg) throws IOException {
        MSGs.addMsg(msg);
        for (int i = 0; i < users.size(); i++) {
            users.get(i).sendMsg(msg); //sending messages to each connected user
        }
    }

    static void removeUser(User u) throws IOException {
        String name = u.getUName();
        Server.sendToAll(name + " disconnected from the chat.");
        u.stopUser();
        users.remove(u); //when a user is disconnected, its reference has to be deleted.
    }

    public static void listenForInput() throws Exception {

        Scanner console = new Scanner(System.in);
        while (true) {
            while (!console.hasNextLine()) {

                Thread.sleep(1);

            }
            String input = console.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

        }

        console.close();
        shutDownServer();

    }

    public Server() {

    }

    public static void shutDownServer() {
        try {
            Server.sendToAll("/offline");
            registrar.stopRegistrar();
            
            for (int i = 0; i < users.size(); i++) {
                users.get(i).stopUser();
            }

            users.clear();

            admin.stopRegistrar();
            System.out.println("server is off");
            //System.exit(0);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
