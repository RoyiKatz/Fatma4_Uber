import java.util.HashMap;

public class InformationSystem {

	private UnboundedBuffer<Ride> delivery_calls, taxi_calls;
	private int deliveries, taxi_rides;		//counters
	private HashMap<String, Integer> service_area_counter;	// area counter


	// constructor
	public InformationSystem() {
		delivery_calls = new UnboundedBuffer<Ride>();
		taxi_calls = new UnboundedBuffer<Ride>();
		deliveries = 0;
		taxi_rides = 0;
		
		service_area_counter = new HashMap<String, Integer>();
		// initialize map
	}
	
	
	// getters
	public int numOfDeliveries() {
		return deliveries;
	}
	
	public int numOfTaxiRides() {
		return taxi_rides;
	}


	// add call
	public void addCall(Ride call) {

		if (call.details().type().equals("Taxi")) {
			taxi_calls.insert(call);
			taxi_rides++;
		} else {
			delivery_calls.insert(call);
			deliveries++;
		}
		
	}

	
	// extract a service call from a given list (Delivery/Taxi)
	public Ride extract(String list) throws InterruptedException {

		return (list.equals("Taxi")) ? taxi_calls.extract() : delivery_calls.extract();
	}

	
	public boolean isEmpty(String buffer) {
		if (buffer.equals("Taxi")) {
			return taxi_calls.isEmpty();
		} else {
			return delivery_calls.isEmpty();
		}
	}

}
