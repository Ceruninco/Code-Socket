/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class that represents the server using multiple threads: one for each connection
 */
public class EchoServerMultiThreaded  {

	/**
	 * A queue where all messages are stored and is available to all server threads
	 * without concurrence problems
	 */
	private Queue<String> history;

 	/**
  	* Constructor that initiates the queue representing the history of messages
  	* 
  	**/
	EchoServerMultiThreaded(){
		history = new ConcurrentLinkedQueue<>();

	}

	/**
	 * Method that starts the server, reads the file containing all the messages
	 * and adds them to the <code>history queue</code> and then listens to
	 * accept new connections, creating a <code>ServerThread</code> for each one
	 *
	 * @param args the list of arguments that should only contain the port
	 */
	public void runServer(String args[]){
        ServerSocket listenSocket;

		if (args.length != 1) {
			  System.out.println("Usage: java EchoServer <EchoServer port>");
			  System.exit(1);
		}
		try {
			File reader = new File("history.txt");

			if (reader.createNewFile()) {
				System.out.println("File created: " + reader.getName());
			} else {
				System.out.println("File already exists.");
			}

			Scanner myScanner = new Scanner(reader);
			while(myScanner.hasNextLine()){
				history.add(myScanner.nextLine());
			}
			myScanner.close();

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

	/**
	 * Method used to add a message to the history queue
	 * @param message the message to add
	 */
	public void addToHistory(String message){
		  this.history.add(message);
		  try {
			  FileWriter historyFileWriter = new FileWriter("history.txt", true);
			  historyFileWriter.write(message+"\n");
			  historyFileWriter.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }

	public Queue<String> getHistory() {
		return history;
	}
}

  
