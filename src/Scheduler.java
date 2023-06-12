
public class Scheduler extends Employee {

	private String area;
	private UnboundedBuffer<ServiceCall> calls;
	private InformationSystem IS;
	private UnboundedBuffer<Vehicle> vehicles;


	public Scheduler(int id, String area, UnboundedBuffer<ServiceCall> calls,
			InformationSystem IS, UnboundedBuffer<Vehicle> vehicles) {
		super(id);
		this.area = area;
		this.calls = calls;
		this.IS = IS;
		this.vehicles = vehicles;
	}


	// work logic
	protected void work() {
		try {
			// grab a call and check it
			ServiceCall call = calls.extract();
			checkCall(call);
		} catch (InterruptedException e) {}
	}


	// end of day
	protected void endDay() {
		System.out.println("Scheduler " + id + " finished");

	}


	// check whether to accept a call or not
	private void checkCall(ServiceCall call) {

		// check area
		if (area.equals(call.area())) {

			handleCall(call);

		} else {
			// area doesn't match
			calls.insert(call);
		}
	}

	// handle a service call
	private void handleCall(ServiceCall call) {

		// find and remove vehicle
		Vehicle v = findVehicle(call);

		// sleep
		double time_to_sleep = v.calculateDrivingTime(call.distance()) /4 *100;
		try {
			Thread.sleep((long)(time_to_sleep));
			// get payed
			getPayed((time_to_sleep / 1000) * 3);
		} catch (InterruptedException e) {}

		// adding to IS
		IS.addCall(new Ride(call, v));
		printCall(call);

	}


	// find vehicle for a call
	private Vehicle findVehicle(ServiceCall call) {
		Vehicle vehicle = null;
		try {
			vehicle = vehicles.extract();
			while (!vehicle.isCompatible(call.type())) {
				vehicles.insert(vehicle);
				vehicle = vehicles.extract();
			}
		} catch (InterruptedException e) {}

		return vehicle;
	}


	// call announcement
	private void printCall(ServiceCall call) {
		System.out.println("New service call (id: " + call.id() + ") arrived and data inserted to database");
	}


}
