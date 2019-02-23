/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wmall
 */
public class ServerShutDown extends Thread {

    @Override
    public void run() {

        try {
            listenForInput();
        } catch (Exception ex) {
            Logger.getLogger(ServerShutDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listenForInput() throws Exception {

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
       
        

    }

   
}
