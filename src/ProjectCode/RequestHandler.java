package ProjectCode;

import java.net.Socket;

import httpPack.HttpRequest;
import httpPack.HttpResponse;
import  org.apache.log4j.Logger;

public class RequestHandler implements Runnable {

	/* Logger - Java ability to capture the log files.
	 * reasons why we may need to capture the application activity :
	 * 1) Getting the info about whats going in the application
	 * 2) Recording unusual circumstances or errors that may be happening in the program
	 * 
	 *  */
	private static Logger log = Logger.getLogger(RequestHandler.class);

	// A socket is an endpoint for communication between two machines.
	// Creates an unconnected socket, with the system-default type of SocketImpl.
	private Socket socket;

	//
	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		
		// opening new request
		try {
			
			// a request to read from the resources
			HttpRequest req = new HttpRequest(socket.getInputStream());
			try{
				
				// goes into the correct situation socket according to the cases(from method enum)
				HttpResponse res = new HttpResponse(req);
				res.write(socket.getOutputStream());
				
				// closes the socket
				socket.close();
				
				// no correct method found
			}catch(NullPointerException npe){}
		} catch (Exception e) {
			log.error("Runtime Error", e);
		}
	}
}
