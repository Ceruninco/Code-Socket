package stream;

public class MainServer {
    public static void main(String args[]){
        EchoServerMultiThreaded e = new EchoServerMultiThreaded();
        e.runServer(args);
    }
}
