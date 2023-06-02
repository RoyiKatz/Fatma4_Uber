

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

		while (IS.isEmpty()) {

		}

		// attempt to grab a call
		grabCall();

	}


	//grab a call
	private void grabCall() {


		UnboundedBuffer<ServiceCall> calls = chooseCalls();

		// grab a call
		try {
			ServiceCall call = calls.extract();
			makeRideFrom(call);
		} catch (InterruptedException e) {}
		
	}
	
	
	// choose service calls queue from information system
	private UnboundedBuffer<ServiceCall> chooseCalls(){
		double chance = Math.random();

		return (chance < 0.5) ? IS.deliveryCalls() : IS.taxiCalls();
	}


	// make a ride from a service call
	private void makeRideFrom(ServiceCall call) {
		// getting a car
		Vehicle v = findVehicle();

		// creating instance of ReadyRide
		ReadyRide ride = new ReadyRide(call, v);

		rides.insert(ride);

	}

	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

}
