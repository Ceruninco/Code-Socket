/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

public class ClientListenThread
        extends Thread {

    private MulticastSocket clientSocket;
    private InetAddress inetAdress;
    private int port;

    ClientListenThread(MulticastSocket s, InetAddress inetAdress) {
        this.clientSocket = s;
        this.inetAdress = inetAdress;
        this.port = s.getLocalPort();
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            System.out.println("Thread created");
            while (true) {
                byte[] buf = new byte[1000];

                DatagramPacket recv = new  DatagramPacket(buf, buf.length);

                // Receive a datagram packet response
                clientSocket.receive(recv);
                int size = ByteBuffer.wrap(Arrays.copyOfRange(buf, 0, 4 )).getInt();

                String line = new String(Arrays.copyOfRange(buf, 4, 4+size ));

                System.out.println("echo: " + line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
        try {
            clientSocket.leaveGroup(inetAdress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


