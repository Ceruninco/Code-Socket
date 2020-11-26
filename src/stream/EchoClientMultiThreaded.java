/***
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;

public class EchoClientMultiThreaded {


    /**
     *  main method
     *  accepts a connection and creates two threads: one to listen and one to write
     **/
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        String nickname = null;
        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port> ");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
            System.out.println("Write your name : ");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            nickname = reader.readLine();

            /*ClientListenThread cl = new ClientListenThread(echoSocket, nickname);
            ClientWriteThread cw = new ClientWriteThread(echoSocket, nickname);

            cl.start();
            cw.start();*/
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Couldn't get I/O for "
                    + "the connection to:"+ args[0]);
            System.exit(1);
        }


        //echoSocket.close();
    }
}


