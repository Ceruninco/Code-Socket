package stream;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.*;

/**
 * Thread used to listen for incoming messages on the client side
 */
public class ClientListenThread
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

    /**
     * Constructor of the ClientListenThread
     * @param s the client's socket
     * @param nickname of the client
     * @param scene the scene where to write the incoming messages
     */
    ClientListenThread(Socket s, String nickname, Scene scene) {
        this.clientSocket = s;
        this.nickname=nickname;
        this.scene = scene;
    }

    /**
     * Reads the input stream and prints the incoming messages
     * in the chat
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
                TextArea area = (TextArea)scene.lookup("#largeTextArea");
                area.appendText(line + "\n");
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


