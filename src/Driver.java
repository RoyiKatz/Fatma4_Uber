
public class Driver extends Employee {

	private char license;
	
	public Driver(int id, char license) {
		super(id);
		this.license = license;
	}
	
	
	// check license against a vehicle
	public boolean licenseMatch(Vehicle v) {
		if (v == null) {
			System.out.println("We couldn't complete the action");
			return false;
		}
		if (v instanceof Taxi) {
			return license == 'B';
		} else {
			return license == 'A';
		}
	}

}
