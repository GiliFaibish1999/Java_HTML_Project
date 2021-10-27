package httpPack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class HttpResponse {

	private static Logger log = Logger.getLogger(HttpResponse.class);

	public static final String VERSION = "HTTP/1.1";

	List<String> headers = new ArrayList<String>();

	byte[] body;

	// response to request depends on the request
	public HttpResponse(HttpRequest req) throws IOException {

		//  the switch statement can have a number of possible execution paths
		// here those paths are different response cases
		// goes into the correct situation socket according to the cases(from method enum)
		
		switch (req.getMethod()) {
			case HEAD:
				fillHeaders(Status._200);
				break;
			case GET:
				try {
					// TODO 
					File file = new File("." + req.uri);
					if (file.isDirectory()) {
					    fillHeaders(Status._200);
						headers.add(ContentType.HTML.toString());
						StringBuilder result = new StringBuilder("<html><head><title>Index of ");
						result.append(req.uri);
						result.append("</title></head><body><h1>Index of ");
						result.append(req.uri);
						result.append("</h1><hr><pre>");

						// TODO add Parent Directory
						File[] files = file.listFiles();
						for (File subfile : files) {
							result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
						}
						result.append("<hr></pre></body></html>");
						fillResponse(result.toString());
					} else if (file.exists()) {  //200 OK
						log.info("200 OK : " + req.uri);
						log.info("Content-Length: " +file.length());

						// Prints the file content
						try (BufferedReader br = new BufferedReader(new FileReader(file))) {
							   String line;
							   System.out.println("");
							   while ((line = br.readLine()) != null) {
							       System.out.println(line);
							   }
							}catch(Exception e0) {
								System.out.println("could not print file content");
							}
						
					    fillHeaders(Status._200);
						setContentType(req.uri, headers);
						fillResponse(getBytes(file));
					} 
					// 404 file not found
					else {
						log.info("404 File not found : " + req.uri);
						fillHeaders(Status._404);
						fillResponse(Status._404.toString());
					}
				} catch (Exception e) {
					log.error("Response Error", e);
					log.info("400 bad request : " + req.uri);
					fillHeaders(Status._400);
					fillResponse(Status._400.toString());
				}

				break;
			case UNRECOGNIZED:
				log.info("400 bad request : " + req.uri);
				fillHeaders(Status._400);
				fillResponse(Status._400.toString());
				break;
			default:
				fillHeaders(Status._501);
				fillResponse(Status._501.toString());
		}

	}

	// for the switcher
	// A switch works with the byte, short, char, and int primitive data types. 
	private byte[] getBytes(File file) throws IOException {
		int length = (int) file.length();
		byte[] array = new byte[length];
		InputStream in = new FileInputStream(file);
		int offset = 0;
		
		// Counts the file size
		while (offset < length) {
			int count = in.read(array, offset, (length - offset));
			offset += count;
		}
		in.close();
		return array;
	}

	// Creator for response headers
	private void fillHeaders(Status status) {
		headers.add(HttpResponse.VERSION + " " + status.toString());
		headers.add("Connection: close");
		headers.add("Server: SimpleWebServer");
	}

	private void fillResponse(String response) {
		body = response.getBytes();
	}

	private void fillResponse(byte[] response) {
		body = response;
	}

	public void write(OutputStream os) throws IOException {
		DataOutputStream output = new DataOutputStream(os);
		for (String header : headers) {
			output.writeBytes(header + "\r\n");
		}
		output.writeBytes("\r\n");
		if (body != null) {
			output.write(body);
		}
		output.writeBytes("\r\n");
		output.flush();
	}

	private void setContentType(String uri, List<String> list) {
		try {
			String ext = uri.substring(uri.indexOf(".") + 1);
			list.add(ContentType.valueOf(ext.toUpperCase()).toString());
		} catch (Exception e) {
			log.error("ContentType not found: " + e, e);
		}
	}
}
