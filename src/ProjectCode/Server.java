package ProjectCode;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.*;


// Main class that starts the Web Server Thread Pool in port 9095
public class Server {
	
	// Main file to read - the homepage
	public static  String  Main_File = "index.html";
	
	// Logger variable definition
	private static Logger log = Logger.getLogger(Server.class);

	// Default port
	private static final int DEFAULT_PORT = 9095;

	/* 
	 * ExecutorService - An Executor(An object that executes submitted Runnable tasks) that provides methods to manage termination and 
	 * methods that can produce a Future for tracking progress of one or more asynchronous tasks.
	 * 
	 * Java creates a cached thread pool when we call Executors.newCachedThreadPool():
	 * The cached pool starts with zero threads and can potentially grow to have Integer.MAX_VALUE threads. 
	 * Practically, the only limitation for a cached thread pool is the available system resources.
	 * The cached thread pool configuration caches the threads (hence the name) for a short amount of time to reuse them for other tasks.
	 * As a result, it works best when we're dealing with a reasonable number of short-lived tasks. 
	 * */
	public static ExecutorService executor = Executors.newCachedThreadPool();

	
	public static void main(String args[]) {
		
		/*
		 *  Add a ConsoleAppender that uses PatternLayout using the PatternLayout.TTCC_CONVERSION_PATTERN
		 *   and prints to System.out to the root category.
		 *   ~ ConsoleAppender : appends log events to System.out or System.err using a layout specified by the user.
		 *    The default target is System.out.
		 *    ~ PatternLayout : A flexible layout configurable with pattern string. 
		 *    The goal of this class is to format a LoggingEvent and return the results as a String. 
		 *    The results depend on the conversion pattern.
		 *    ~ PatternLayout.TTCC_CONVERSION_PATTERN : A conversion pattern equivalent to the TTCCCLayout. Current value is %r [%t] %p %c %x - %m%n.
		 * */
		try {
			BasicConfigurator.configure();
		} catch (Exception e1) {
			System.out.println("could not configure");
			log.error("could not configure", e1);
		}
		
		// starts the Web Server in port 9095
		try {
			new Server().start(getValidPortParam(args));
		} catch (Exception e) {
			log.error("Startup Error", e);
		}
		
	}

	// creates a socket with the requested port number
	@SuppressWarnings("resource")
	public void start(int port) throws IOException {
		ServerSocket s = new ServerSocket(port);
		System.out.println("Server is listening on port " + port);
		while (true) {
			System.out.println(" ");
			
			/*It extracts the first
       			connection request on the queue of pending connections for the
       			listening socket
			 * */
			executor.submit(new RequestHandler(s.accept()));
		}
	}

	/**
	 * Parse command line arguments (string[] args) for valid port number
	 * 
	 * @return int valid port number or default value (9095)
	 */
	static int getValidPortParam(String args[]) throws NumberFormatException {
		if (args.length > 0) {
			int port = Integer.parseInt(args[0]);
			if (port > 0 && port < 65535) {
				return port;
			} else {
				throw new NumberFormatException("Invalid port! Port value is a number between 0 and 65535");
			}
		}
		return DEFAULT_PORT;
	}
}
