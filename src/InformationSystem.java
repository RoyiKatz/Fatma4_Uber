
public class InformationSystem {

	private UnboundedBuffer<ServiceCall> delivery_calls, taxi_calls;


	// constructor
	public InformationSystem() {
		delivery_calls = new UnboundedBuffer<ServiceCall>();
		taxi_calls = new UnboundedBuffer<ServiceCall>();
	}


	// add call
	public synchronized void addCall(ServiceCall call) {

		if (call.type().equals("Taxi")) {
			taxi_calls.insert(call);
		} else {
			delivery_calls.insert(call);
		}

	}


	// getters
	public UnboundedBuffer<ServiceCall> deliveryCalls(){
		return delivery_calls;
	}

	public UnboundedBuffer<ServiceCall> taxiCalls(){
		return taxi_calls;
	}


	// is empty
	public boolean isEmpty() {
		return delivery_calls.isEmpty() && taxi_calls.isEmpty();
	}

}
