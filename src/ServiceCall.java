
public class ServiceCall implements Comparable<ServiceCall> {

	private int id;
	private Customer customer;
	private String service_type, service_area;
	private double distance;
	
	public ServiceCall(int id, Customer customer, String service_type, String service_area, double distance) {
		
		this.id = id;
		this.customer = customer;
		this.service_type = service_type;
		this.service_area = service_area;
		this.distance = distance;
		
	}
	
	
	// getters
	public double distance() {
		return distance;
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
	
	
	
}
