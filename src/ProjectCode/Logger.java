package ProjectCode;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/*The logger class provides methods for logging. Since LogManager is the one doing actual logging, 
 * its instances are accessed using the LogManager‘s getLogger method.
 * The global logger instance is accessed through Logger class’ static field GLOBAL_LOGGER_NAME (here "name"). 
 * It is provided as a convenience for making casual use of the Logging package.
 * */
public class Logger {

	// A Logger object is used to log messages for a specific system or application component. 
	java.util.logging.Logger logger;

	public Logger(String name) {
		
		// Find or create a logger for a named subsystem.
		logger = java.util.logging.Logger.getLogger(name);
		
		// Set the log level specifying which message levels will be logged by this logger.
		// here the level INFO = General Info
		logger.setLevel(Level.INFO);
		logger.addHandler(new ConsoleHandler());
	}

	@SuppressWarnings("rawtypes")
	
	/*This method is used to find or create a logger with the name passed as parameter. 
	 * It will create a new logger if logger does not exist with the passed name.
	 * Create a Logger with class name of the class we put in the function (cl)
	 * */
	public static Logger getLogger(Class cl) {
		return new Logger(cl.getName());
	}

	// error definition - used when logger indicates some serious failure
	public void error(String msg, Throwable thrown) {
		logger.log(Level.SEVERE, msg, thrown);
	}

	// Log an INFO message.
	// here used for requests and responses messages
	public void info(String msg) {
		logger.info(msg);
	}
}
