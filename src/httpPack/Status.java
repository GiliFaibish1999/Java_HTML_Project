package httpPack;

//Enumerations serve the purpose of representing a group of named constants in a programming language
//in this case - the enum is used for the response statuses 
public enum Status {

	// Responses
	_200("200 OK"), 
	_400("400 Bad Request"), 
	_404("404 Not Found"),
	_501("501 Not Implemented");  // might need to delete this

	private final String status;

	Status(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}