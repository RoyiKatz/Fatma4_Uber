
public class InformationSystem {

	private UnboundedBuffer<ServiceCall> delivery_calls, taxi_calls;


	// constructor
	public InformationSystem() {
		delivery_calls = new UnboundedBuffer<ServiceCall>();
		taxi_calls = new UnboundedBuffer<ServiceCall>();
	}


	// add call
	public void addCall(ServiceCall call) {

		if (call.type().equals("Taxi")) {
			taxi_calls.insert(call);
		} else {
			delivery_calls.insert(call);
		}
		
	}

	
	// extract a service call from a given list (Delivery/Taxi)
	public ServiceCall extract(String list) throws InterruptedException {

		return (list.equals("Taxi")) ? taxi_calls.extract() : delivery_calls.extract();
	}

}
