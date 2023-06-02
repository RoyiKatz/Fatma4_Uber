
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
		
		this.notifyAll();

	}


	// is empty
	public boolean isEmpty() {
		return delivery_calls.isEmpty() && taxi_calls.isEmpty();
	}
	
	
	// extract a service call from a given list (Delivery/Taxi)
	public synchronized ServiceCall extract(String list) throws InterruptedException {
		
		while (this.isEmpty()) {
			this.wait();
		}
		return (list.equals("Taxi")) ? taxi_calls.extract() : delivery_calls.extract();
	}

}
