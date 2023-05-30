
public class Request {

	private int customer_id;
	private String service_type, service_area;
	private double distance;
	
	
	// constructor
	public Request(int customer_id, String service_type, String service_area, double distance) {
		
		this.customer_id = customer_id;
		
		//check service type
		checkService(service_type);
		
		this.service_area = service_area;
		this.distance = distance;
	}
	
	// check a given service type
	private void checkService(String service) {
		if (service.equals("Delivery") || service.equals("Taxi")) {
			service_type = service;
		} else {
			throw new ServiceException(service + " is not a valid service.");
		}
	}
	
	
	// getters
	public int customerID() {
		return customer_id;
	}
	
	public String serviceType() {
		return service_type;
	}
	
	public String area() {
		return service_area;
	}
	
	public double distance() {
		return distance;
	}
	
}
