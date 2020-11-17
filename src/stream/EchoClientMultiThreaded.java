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
     *  accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        InetAddress groupAddr = null;

        int groupPort = 0;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            groupAddr = InetAddress.getByName(args[0]);
            groupPort = Integer.parseInt(args[1]);

            MulticastSocket multicastSocket = new MulticastSocket(groupPort);

            ClientListenThread cl = new ClientListenThread(multicastSocket, groupAddr);
            ClientWriteThread cw = new ClientWriteThread(multicastSocket,groupAddr);

            cl.start();
            cw.start();
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


