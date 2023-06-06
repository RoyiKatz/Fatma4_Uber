
public class Driver extends Employee {

	private char license;
	private BoundedBuffer<ReadyRide> rides;
	private double total_profit, total_distance;
	private Manager manager;
	private UnboundedBuffer<Vehicle> vehicles;

	public Driver(int id, char license, BoundedBuffer<ReadyRide> rides,
			Manager manager, UnboundedBuffer<Vehicle> vehicles) {
		super(id);
		this.license = license;
		this.rides = rides;
		total_distance = 0;
		total_profit = 0;
		this.manager = manager;
		this.vehicles = vehicles;
	}

	
	protected void work() {
		// try to grab a ride
		try {
			grabDrive();
		} catch (InterruptedException e) {}
	}
	
	
	// end of day
	protected void endDay() {
		System.out.println("Driver " + id + " finished");
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


	// try to grab a ride
	private void grabDrive() throws InterruptedException {
		ReadyRide ride = rides.extcract();

		if (licenseMatch(ride.vehicle())) {
			// simulate drive
			drive(ride);

			// end drive
			endRide(ride);

			// returning vehicle
			vehicles.insert(ride.vehicle());
			System.out.println("Driver " + id + " returned vehicle " + ride.vehicle().licenseNumber());

		} else {
			// license doesn't match
			rides.insert(ride);
		}
	}


	// simulate drive
	private void drive(ReadyRide ride) {
		
		System.out.println("Driver " + id + " driving call " + ride.details().id());

		double driving_distance = ride.details().distance();
		long driving_time = (long)(ride.vehicle().calculateDrivingTime(driving_distance) * 1000);

		try {
			Thread.sleep(driving_time);
		} catch (InterruptedException e) {}


		// update total distance
		total_distance += driving_distance;

	}


	// drop passenger and calculate profit
	private void endRide(ReadyRide ride) {

		// drop passenger
		dropPassenger();

		//calculate profit
		calculateProfit(ride);

		// alert manager
		System.out.println("finished drive (id: " + ride.details().id + ")");
		manager.updateRides();
		
	}


	// dropping passenger and getting rating
	private void dropPassenger() {

		// simulate
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}

	}


	//calculate profit
	private void calculateProfit(ReadyRide ride) {

		Customer passenger = ride.details().customer();
		double P = (Math.random() * 0.5) + 0.5;

		// get rating
		int rating = passenger.giveRating();


		double distance = ride.details().distance();
		double time = ride.vehicle().calculateDrivingTime(distance);

		double customer_payment = passenger.pay(time, ride.vehicle().fare());
		double profit = (customer_payment + rating) - (time * P);

		total_profit += profit;
	}


}
