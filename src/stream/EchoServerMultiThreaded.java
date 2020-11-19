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

public class EchoServerMultiThreaded  {
	private Queue<String> history;

 	/**
  	* main method
	* @ param EchoServer port
  	* 
  	**/
	EchoServerMultiThreaded(){
		history = new ConcurrentLinkedQueue<>();

	}

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

  
