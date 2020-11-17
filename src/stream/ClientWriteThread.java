/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientWriteThread
        extends Thread {

    private MulticastSocket clientSocket;
    private InetAddress inetAdress;
    private int port;

    ClientWriteThread(MulticastSocket s, InetAddress inetAdress) {
        this.clientSocket = s;
        this.inetAdress = inetAdress;
        this.port = s.getLocalPort();
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            clientSocket.joinGroup(inetAdress);


            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String line = stdIn.readLine();

                if (line.equals(".")) break;
                int size = line.length();

                byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
                byte[] stringBytes = line.getBytes();


                byte[] c = new byte[bytes.length + stringBytes.length];
                System.arraycopy(bytes, 0, c, 0, bytes.length);
                System.arraycopy(stringBytes, 0, c, bytes.length, stringBytes.length);
                DatagramPacket hi = new DatagramPacket(c, c.length, inetAdress, port);
                clientSocket.send(hi);
            }

            clientSocket.leaveGroup(inetAdress);
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


