package stream;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Loader extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {

        Parent root = FXMLLoader.load(getClass().getResource("chatLayout.fxml"));
        stage.setTitle("TCP chat");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
