/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Thread that was used to send messages from the client to the server
 * when there was no GUI :
 * listens the stream of the console and send a message to the server
 */
public class ClientWriteThread
        extends Thread {

    /**
     * Socket of the client
     */
    private Socket clientSocket;
    /**
     * The nickname of the client
     */
    private String nickname;
    /**
     * The scene associated to this client, used to write
     * the incoming messages
     */
    private Scene scene;

    ClientWriteThread(Socket s, String nickname, Scene scene) {
        this.clientSocket = s;
        this.nickname = nickname;
        this.scene = scene;
    }

    /**
     * receives a request from client's console then sends it to the server
     **/
    public void run() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {

                String line = stdIn.readLine();


                if (line.equals(".")) break;

                socOut.println(nickname + " : " + line);
            }
            socOut.close();
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


