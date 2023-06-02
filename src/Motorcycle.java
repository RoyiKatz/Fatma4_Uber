
public class Motorcycle extends Vehicle {

	public Motorcycle(int licenseNumber, String model, int year) {
		super(licenseNumber, model, year);
		base_fare = 0;
	}


	@Override
	protected String getType() {
		return "Motorcycle";
	}

}
