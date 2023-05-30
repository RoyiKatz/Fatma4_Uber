
public class InformationSystem {
	
	private UnboundedBuffer<ServiceCall> delivery_calls, taxi_calls; //check for better way
	
	
	// constructor
	public InformationSystem() {
		delivery_calls = new UnboundedBuffer<ServiceCall>();
		taxi_calls = new UnboundedBuffer<ServiceCall>();
	}
	
	
	// add call
	public void addCall(ServiceCall call) {
		
		if (call.type().equals("Taxi")) {
			taxi_calls.add(call);
		} else {
			delivery_calls.add(call);
		}
	}
	
}
