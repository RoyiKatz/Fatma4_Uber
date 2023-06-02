
public abstract class Vehicle {

	protected int licenseNumber;
	protected String model;
	protected int year;
	protected double base_fare;


	// constructor
	public Vehicle(int licenseNumber, String model, int year) {

		this.licenseNumber = licenseNumber;
		this.model = model;

		//check year validity
		if (year >= 1970 && year <= 2022) {
			this.year = year;
		} else {
			throw new YearException("invalid year of production - " + year + ". Year must be between 1970-2022");
		}

	}


	// getters
	public int year() {
		return year;
	}

	public int licenseNumber() {
		return licenseNumber;
	}

	public String model() {
		return model;
	}
	
	public double fare() {
		return base_fare;
	}


	// calculate driving time based on a given distance
	public double calculateDrivingTime(double distance) {

		double P = 0.5 + (Math.random() * 0.2);

		return (distance / (100 * P)) + 2;

	}

	//return a string representation of the type of vehicle
	abstract protected String getType();


	//toString
	public String toString() {
		String type = getType();
		return "Type: " + type + ", License: " + licenseNumber + ", Model: " + model + ", Year: " + year;
	}

}
