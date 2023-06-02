
public class Driver extends Employee implements Runnable{

	private char license;
	private BoundedBuffer<ReadyRide> rides;
	private double total_profit, total_distance;
	
	public Driver(int id, char license, BoundedBuffer<ReadyRide> rides) {
		super(id);
		this.license = license;
		this.rides = rides;
		total_distance = 0;
		total_profit = 0;
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


	@Override
	public void run() {
		
		// try to grab a ride
		try {
			grabDrive();
		} catch (InterruptedException e) {}
		
	}
	
	
	// try to grab a ride
	private void grabDrive() throws InterruptedException {
		ReadyRide ride = rides.extcract();
		
		if (licenseMatch(ride.vehicle())) {
			// simulate drive
			drive(ride);
			
			// dropping passenger
			Customer passenger = ride.details().customer();
			drop(passenger);
			
			// returning vehicle
			
		} else {
			// license doesn't match
			rides.insert(ride);
		}
	}
	
	
	// simulate drive
	private void drive(ReadyRide ride) {
		
		double driving_distance = ride.details().distance();
		long driving_time = (long)(ride.vehicle().calculateDrivingTime(driving_distance) * 1000);
		
		try {
			Thread.sleep(driving_time);
		} catch (InterruptedException e) {}
		
	}
	
	
	// dropping passenger
	private void drop(Customer passenger) {
		
		// simulate
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		// get rating
		int rating = passenger.giveRating();
		
		// calculate profit
	}
	


}
