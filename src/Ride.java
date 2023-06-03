
public class Ride {

	protected ServiceCall order;
	protected Vehicle vehicle;
	
	
	// constructor
	public Ride(ServiceCall call, Vehicle v) {
		order = call;
		vehicle = v;
	}
	
	
	// getters
	public Vehicle vehicle() {
		return vehicle;
	}
	
	public ServiceCall details() {
		return order;
	}
}
