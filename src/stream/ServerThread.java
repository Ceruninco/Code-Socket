/***
 * ServerThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing a separate thread of the server responsible
 * for the connection with one specific client
 */
public class ServerThread
		extends Thread {

	private Socket clientSocket;

	/**
	 * Id of the client which will be the key in the map <code>activeClients</code>
	 */
	static int counter = 0;

	/**
	 * The main server
	 */
	EchoServerMultiThreaded serverMultiThreaded;

	/**
	 * Map of active clients connected to the chat
	 */
	static ConcurrentHashMap<Integer, Socket> activeClients = new ConcurrentHashMap<Integer, Socket>();

	/**
	 * Map of output streams of the active clients connected to the chat
	 */
	static ConcurrentHashMap<Integer, PrintStream> activeStreams = new ConcurrentHashMap<Integer, PrintStream>();

	ServerThread(Socket s, EchoServerMultiThreaded serverMultiThreaded) {
		this.clientSocket = s;
		this.serverMultiThreaded = serverMultiThreaded;
	}

	/**
	 * When a new connection is established, send him all the history and
	 * then listens for incoming messages from the associated client:
	 * when a message arrives, sends it back to all the clients connected,
	 * including the client who sent the message
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
			counter++;

			Queue<String> history = serverMultiThreaded.getHistory();

			if (!history.isEmpty()){
				for (String s: history) {
					socOut.println(s);
				}
			}


			while (true) {
				//String l = stdIn.readLine();
				//socOut.println(l);

				String line = socIn.readLine();
				serverMultiThreaded.addToHistory(line);
				//socOut.println(line);


				// broadcast message to all available clients
				for(int clientHost : activeStreams.keySet()) {
					// get each socket here and send a message to them
					activeStreams.get(clientHost).println(line);
					//System.out.println(activeClients.get(clientHost).getInetAddress().getHostName() + " " + activeClients.size())
				}
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}


