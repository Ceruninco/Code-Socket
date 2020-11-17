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
import java.net.Socket;

public class ClientWriteThread
        extends Thread {

    private Socket clientSocket;



    ClientWriteThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line = stdIn.readLine();
                if (line.equals(".")) break;
                socOut.println(line);
            }
            socOut.close();
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


