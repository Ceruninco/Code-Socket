/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientListenThread
        extends Thread {

    private Socket clientSocket;
    private String nickname;


    ClientListenThread(Socket s, String nickname) {
        this.clientSocket = s;
        this.nickname=nickname;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Thread created");
            while (true) {

                String line = socIn.readLine();

                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


