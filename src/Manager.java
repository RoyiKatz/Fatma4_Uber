
public class Manager extends Thread {

	private UnboundedBuffer<Request> requests;
	
	
	// constructor
	public Manager(UnboundedBuffer<Request> requests) {
		this.requests = requests;
	}
	
}
