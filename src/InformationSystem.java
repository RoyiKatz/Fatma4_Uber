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
		
		// initialize map
		service_area_counter = new HashMap<String, Integer>();
		service_area_counter.put("Tel Aviv", 0);
		service_area_counter.put("Jerusalem", 0);
	}
	
	
	// getters
	public int numOfDeliveries() {
		return deliveries;
	}
	
	public int numOfTaxiRides() {
		return taxi_rides;
	}


	// add call
	public synchronized void addCall(Ride call) {
		
		// update service area counter
		String area = call.details().area();
		int occurence = service_area_counter.get(area);
		service_area_counter.replace(area, occurence + 1);

		if (call.details().type().equals("Taxi")) {
			taxi_calls.insert(call);
			taxi_rides++;
		} else {
			delivery_calls.insert(call);
			deliveries++;
		}
		
		this.notifyAll();
	}

	
	// extract a service call from a given list (Delivery/Taxi)
	public Ride extract(String list) throws InterruptedException {
		return (list.equals("Taxi")) ? taxi_calls.extract() : delivery_calls.extract();
	}

	
	// return whether a specific queue of rides is empty
	public boolean isEmpty(String buffer) {
		if (buffer.equals("Taxi")) {
			return taxi_calls.isEmpty();
		} else {
			return delivery_calls.isEmpty();
		}
	}
	
	// return whether both rude queues are empty
	public boolean isEmpty() {
		return taxi_calls.isEmpty() && delivery_calls.isEmpty();
	}
	
	
	// return the most popular area
	public String mostPopularArea() {
		int tel_aviv = service_area_counter.get("Tel Aviv");
		int jerusalem = service_area_counter.get("Jerusalem");
		
		return (tel_aviv > jerusalem) ? "Tel Aviv": "Jerusalem";
	}

}
