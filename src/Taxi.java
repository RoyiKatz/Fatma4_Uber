
public class Taxi extends Vehicle {

	public Taxi(int licenseNumber, String model, int year) {
		super(licenseNumber, model, year);
		base_fare = 15;
	}


	@Override
	protected String getType() {
		return "Taxi";
	}


	// calculate driving time based on a given distance
	public double calculateDrivingTime(double distance) {

		double P = 0.5 + (Math.random() * 0.2);

		return (distance / (100 * P)) + 2;

	}

}
