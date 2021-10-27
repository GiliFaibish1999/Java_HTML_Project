package httpPack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class HttpRequest {

	// Logger definition 
	private static Logger log = Logger.getLogger(HttpRequest.class);

	// Request headers
	List<String> headers = new ArrayList<String>();

	// Request line variables
	
	// Method we are using
	private Method method;
	
	// File location on web server
	String uri;

	// HTTP version 
	String version;

	// Parse http request
	// gets input stream, reads the html files
	public HttpRequest(InputStream is) throws IOException {
		
		// Reads text from a character-input stream, buffering characters so as to provide for the efficient 
		// reading of characters, arrays, and lines.
		// reads tempurary files during runtime
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = reader.readLine();
		try{
			parseRequestLine(str);
		}catch(NullPointerException npe){}
		
		// request string contains request method + address(uri) + http version (here HTTP/1.1)
		str = getMethod() + " " +uri + " " + version;
		parseRequestHeader(str);
	}

	private void parseRequestLine(String str) {
		log.info(str);
		String[] split = str.split("\\s+");
		try {
			setMethod(Method.valueOf(split[0]));
		} catch (Exception e) {
			setMethod(Method.UNRECOGNIZED);
		}
		uri = split[1];
		version = split[2];
	}

	// parse the request header from the string
	private void parseRequestHeader(String str) {
		log.info(str);
		headers.add(str);
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
}