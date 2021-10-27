package httpPack;

//Enumerations serve the purpose of representing a group of named constants in a programming language
//in this case - the enum is used for the available content file types
public enum ContentType {
	
	// Content file types
	CSS("CSS"), //
	HTML("HTML"), //
	TXT("TXT"), //
	XML("XML"); //

	private final String extension;

	ContentType(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		switch (this) {
			case CSS:
				return "Content-Type: text/css";
			case HTML:
				return "Content-Type: text/html";
			case TXT:
				return "Content-type: text/plain";
			case XML:
				return "Content-type: text/xml";
			default:
				return null;
		}
	}
}
