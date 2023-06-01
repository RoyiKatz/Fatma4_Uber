
public class ReadyRide {

	private ServiceCall order;
	private Vehicle vehicle;
	
	
	// constructor
	public ReadyRide(ServiceCall call, Vehicle v) {
		order = call;
		vehicle = v;
	}
	
	
	// getters
	public Vehicle vehicle() {
		return vehicle;
	}
}
