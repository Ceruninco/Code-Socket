package stream;

/**
 * Class that creates the server and runs it
 */
public class MainServer {
    public static void main(String args[]){
        EchoServerMultiThreaded e = new EchoServerMultiThreaded();
        e.runServer(args);
    }
}
