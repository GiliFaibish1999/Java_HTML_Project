package httpPack;

// Enumerations serve the purpose of representing a group of named constants in a programming language
// in this case - the enum is used for the request methods names
public enum Method {
	GET("GET"), //
	HEAD("HEAD"),
	UNRECOGNIZED(null);
	
	private final String method;

	Method(String method) {
		this.method = method;
	}
}