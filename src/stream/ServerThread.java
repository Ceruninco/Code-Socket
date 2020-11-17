/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread
		extends Thread {

	private Socket clientSocket;

	/**
	 * Id of the client which will be the key in the map <code>activeClients</code>
	 */
	static int counter = 0;

	private int idClient;
	EchoServerMultiThreaded serverMultiThreaded;
	/**
	 * Map of active clients connected to the chat
	 * (using this map to not bother with synchronisation)
	 */
	static ConcurrentHashMap<Integer, Socket> activeClients = new ConcurrentHashMap<Integer, Socket>();

	static ConcurrentHashMap<Integer, PrintStream> activeStreams = new ConcurrentHashMap<Integer, PrintStream>();

	ServerThread(Socket s, EchoServerMultiThreaded serverMultiThreaded) {
		this.clientSocket = s;
		this.serverMultiThreaded = serverMultiThreaded;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * @ param clientSocket the client socket
	 **/
	public void run() {
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			// add client socket to the map of connected clients
			activeClients.put(counter, clientSocket);
			activeStreams.put(counter, socOut);
			idClient = counter;
			counter++;


			while (true) {
				//String l = stdIn.readLine();
				//socOut.println(l);

				String line = socIn.readLine();
				serverMultiThreaded.addToHistory(line);
				//socOut.println(line);


				// broadcast message to all available clients
				for(int clientHost : activeStreams.keySet()) {
					if (clientHost != idClient) {
						// get each socket here and send a message to them
						activeStreams.get(clientHost).println(line);
						//System.out.println(activeClients.get(clientHost).getInetAddress().getHostName() + " " + activeClients.size());
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}


