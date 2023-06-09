
public class ServiceCall extends Call implements Comparable<ServiceCall> {

	private Customer customer;
	
	public ServiceCall(int id, Customer customer, String service_type, String service_area, double distance) {
		
		super(id, service_type, service_area, distance);
		
		this.customer = customer;
	}
	
	
	// getter
	public Customer customer() {
		return customer;
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
