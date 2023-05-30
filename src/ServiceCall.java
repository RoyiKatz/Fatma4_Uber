
public class ServiceCall implements Comparable<ServiceCall> {

	private int id;
	private Customer customer;
	private String service_type, service_area;
	private double distance;
	
	public ServiceCall(int id, Customer customer, String service_type, String service_area, double distance) {
		
		this.id = id;
		this.customer = customer;
		
		// check service type
		if (service_type.equals("Taxi") || service_type.equals("Delivery")) {
			this.service_type = service_type;
		} else {
			throw new ServiceException(service_type + " is not a valid service.");
		}
		
		this.service_area = service_area;
		this.distance = distance;
		
	}
	
	
	// getters
	public double distance() {
		return distance;
	}
	
	public String type() {
		return service_type;
	}
	
	
	@Override
	public int compareTo(ServiceCall other) {
		if (this.distance == other.distance()) {
			return 0;
		} else if (this.distance > other.distance()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	
	public String toString() {
		String str = "Call ID: " + id + "\n";
		str += "Service Type: " + service_type + "\n";
		str += "Service Area: " + service_area + "\n";
		str += "Driving Distance: " + distance + "m";
		
		return str;
	}
	
}
