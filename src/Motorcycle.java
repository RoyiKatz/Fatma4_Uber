
public class Motorcycle extends Vehicle {
	
	private int max_speed;

	public Motorcycle(int licenseNumber, String model, int year, int max_speed) {
		super(licenseNumber, model, year);
		base_fare = 0;
		this.max_speed = max_speed;
	}


	@Override
	protected String getType() {
		return "Motorcycle";
	}


	//calculate driving time based on a given distance
	public double calculateDrivingTime(double distance) {

		double P = 0.6 + Math.random() * 0.2;

		return distance / (max_speed * P);

	}


	@Override
	public boolean isCompatible(String service) {
		return service.equals("Delivery");
	}

}
