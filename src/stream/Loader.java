package stream;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class for the client that opens the GUI
 */
public class Loader extends Application
{
    /**
     * Nickname of the client
     */
    String nickname = null;


    public static void main(String[] args)
    {
        Application.launch(args);
    }

    /**
     * Creates a new socket that connects to the server and handles the GUI
     * @param stage the associated stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        String []args = {"localhost", "1026"};
        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            Socket echoSocket = null;


            // creation socket ==> connexion
            echoSocket = new Socket(args[0], Integer.parseInt(args[1]));


            Parent root = FXMLLoader.load(getClass().getResource("chatLayout.fxml"));
            stage.setTitle("TCP chat");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            TextArea area = (TextArea)scene.lookup("#largeTextArea");
            area.setEditable(false);

            TextField nicknameField = (TextField)scene.lookup("#nickname");
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            nickname = uuid;

            EventHandler<ActionEvent> nicknameHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setNickname(nicknameField.getText());
                    event.consume();
                }
            };

            Button nicknameButton = (Button)scene.lookup("#changeNickname");
            nicknameButton.setOnAction(nicknameHandler);



            PrintStream socOut = new PrintStream(echoSocket.getOutputStream());

            EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String finalNickname = getNickname();
                    TextArea msg = (TextArea)scene.lookup("#messageTextArea");
                    String mes = msg.getText();
                    socOut.println(finalNickname + " : " + mes);
                    msg.setText("");
                    event.consume();
                }
            };

            Button sendButton = (Button)scene.lookup("#sendMessageButton");
            sendButton.setOnAction(buttonHandler);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            ClientListenThread cl = new ClientListenThread(echoSocket, nickname, scene);

            // ClientWriteThread not really used anymore since it listens to the console
            // to get messages from the client and there is not anymore a need for a separate
            // thread since we get the message everytime the client clicks the "Send" button
            ClientWriteThread cw = new ClientWriteThread(echoSocket, nickname, scene);

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

    }

    public void setNickname(String s){
        nickname = s;
    }

    public String getNickname(){
        return nickname;
    }
}
