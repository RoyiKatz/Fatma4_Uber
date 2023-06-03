

public class CarOfficer extends Employee implements Runnable {

	private InformationSystem IS;
	private BoundedBuffer<ReadyRide> rides;

	public CarOfficer(int id, InformationSystem info_sys, BoundedBuffer<ReadyRide> rides) {
		super(id);
		this.IS = info_sys;
		this.rides = rides;
	}

	@Override
	public void run() {

		// attempt to grab a call
		while (not_finished) {
			work();
		}
		
		System.out.println("Car Officer " + id + " finished");

	}
	
	
	@Override
	protected void work() {
		grabCall();
	}


	//grab a call
	private void grabCall() {

		String choice = chooseBuffer();
		
		System.out.println("Car Officer " + id + " trying to grab " + choice + " call");

		// grab a call
		try {
			ServiceCall call = IS.extract(choice);
			makeRideFrom(call);
		} catch (InterruptedException e) {}

	}


	// choose service calls queue from information system
	private String chooseBuffer(){
		double chance = Math.random();

		return (chance < 0.5) ? "Delivery" : "Taxi";
	}


	// make a ride from a service call
	private void makeRideFrom(ServiceCall call) {
		
		System.out.println("Car Officer " + id + " is making a ride from call " + call.id());
		
		// getting a car
		Vehicle v = findVehicle();

		// creating instance of ReadyRide
		ReadyRide ride = new ReadyRide(call, v);

		rides.insert(ride);

	}

	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		//return null;
		
		return new Taxi(123, "toyota", 2000);
	}

	

}
