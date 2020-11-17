/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EchoServerMultiThreaded  {
	private Queue<String> history = new ConcurrentLinkedQueue<>();
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
	EchoServerMultiThreaded(){

	}
       public static void main(String args[]){
        ServerSocket listenSocket;

  	if (args.length != 1) {
          System.out.println("Usage: java EchoServer <EchoServer port>");
          System.exit(1);
  	}
	try {

		listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
		System.out.println("Server ready..."); 
		while (true) {

			Socket clientSocket = listenSocket.accept();
			System.out.println("Connexion from:" + clientSocket.getInetAddress());

			ServerThread ct = new ServerThread(clientSocket, this);

			ct.start();
		}
	} catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
      }

      public void addToHistory(String message){
			this.history.add(message);
	  }
  }

  
