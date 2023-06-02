
public class Taxi extends Vehicle {

	public Taxi(int licenseNumber, String model, int year) {
		super(licenseNumber, model, year);
		base_fare = 15;
	}


	@Override
	protected String getType() {
		return "Taxi";
	}

}
