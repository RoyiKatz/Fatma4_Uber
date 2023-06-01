
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
		while (rides.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// try to grab a ride
		grabDrive();
		
	}
	
	
	// try to grab a ride
	private synchronized void grabDrive() {
		ReadyRide ride = rides.remove();
		
		if (licenseMatch(ride.vehicle())) {
			// drive
			
		} else {
			rides.add(ride);
		}
	}
	
	


}
