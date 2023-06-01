
public abstract class Call {
	
	protected String service_type, service_area;
	protected double distance;
	
	
	// constructor
	public Call(String service_type, String service_area, double distance) {
				
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
	
	
	public String type() {
		return service_type;
	}
	
	public String area() {
		return service_area;
	}
	
	public double distance() {
		return distance;
	}
}
