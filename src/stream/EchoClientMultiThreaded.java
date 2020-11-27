/***
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;


/**
 * Class that represents the client connected to a multicast group
 */
public class EchoClientMultiThreaded {


    /**
     *  main method
     *  Creates a multicast socket and joins the group with the parameters
     *  specified in <code>args</code> and starts two threads : one for
     *  listening to incoming messages and one to write messages
     *
     * @param args list of two arguments containing the address and the port
     *             of the group to join
     **/
    public static void main(String[] args) throws IOException {

        InetAddress groupAddr = null;

        int groupPort = 0;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClientMultiThreaded <EchoServer host> <EchoServer port>");
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


