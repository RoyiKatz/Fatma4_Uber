
public class Driver extends Employee {

	private char license;
	private BoundedBuffer<ReadyRide> rides;
	private Manager manager;
	private UnboundedBuffer<Vehicle> vehicles;

	public Driver(int id, char license, BoundedBuffer<ReadyRide> rides,
			Manager manager, UnboundedBuffer<Vehicle> vehicles) {
		super(id);
		this.license = license;
		this.rides = rides;
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

		// check license
		if (licenseMatch(ride.vehicle())) {
			// simulate drive
			drive(ride);

			// end drive
			endRide(ride);

			// returning vehicle
			vehicles.insert(ride.vehicle());

		} else {
			// license doesn't match
			rides.insert(ride);
		}
	}


	// simulate drive
	private void drive(ReadyRide ride) {
		
		double driving_distance = ride.details().distance();
		long driving_time = (long)(ride.vehicle().calculateDrivingTime(driving_distance) * 100);

		try {
			Thread.sleep(driving_time);
		} catch (InterruptedException e) {}

	}


	// drop passenger and calculate profit
	private void endRide(ReadyRide ride) {

		// drop passenger
		dropPassenger();

		//calculate profit and get payed
		double profit = calculateProfit(ride);
		getPayed(profit);
		getPayed(ride.driverBonus());

		// alert manager
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
	private double calculateProfit(ReadyRide ride) {

		Customer passenger = ride.details().customer();
		double P = (Math.random() * 0.5) + 0.5;

		// get rating
		int rating = passenger.giveRating();

		
		double distance = ride.details().distance();
		double time = ride.vehicle().calculateDrivingTime(distance);
		
		// calculate profit
		double customer_payment = passenger.pay(time, ride.vehicle().fare());
		double profit = (customer_payment + rating) - (time * P);

		return profit;
	}
	

}
