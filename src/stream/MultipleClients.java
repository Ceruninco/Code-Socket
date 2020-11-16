package stream;

import java.net.Socket;

import java.io.*;
import java.net.*;

public class MultipleClients {
    public static void main(String[] args) {
        try {
            ClientThread client1 = new ClientThread(new Socket("localhost",new Integer(1026).intValue()));
            client1.run();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
