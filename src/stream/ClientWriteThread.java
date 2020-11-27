/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

/**
 * Thread used to read the system console continuously waiting for
 * messages from the client
 */
public class ClientWriteThread
        extends Thread {

    /**
     * The multicast socket to which the client is connected
     */
    private MulticastSocket clientSocket;
    /**
     * The address of the group
     */
    private InetAddress inetAdress;
    /**
     * The port of the group
     */
    private int port;

    /**
     * Constructor of ClientWriteThread
     * @param s the multicast socket
     * @param inetAdress the address of the group
     */
    ClientWriteThread(MulticastSocket s, InetAddress inetAdress) {
        this.clientSocket = s;
        this.inetAdress = inetAdress;
        this.port = s.getLocalPort();
    }

    /**
     * Reads the system console and when the client sends a message,
     * sends it to the group. Leaves the group if a "." is sent
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


