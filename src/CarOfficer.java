

public class CarOfficer extends Employee implements Runnable {

	private InformationSystem info_sys;
	private BoundedBuffer<ReadyRide> rides;

	public CarOfficer(int id, InformationSystem info_sys, BoundedBuffer<ReadyRide> rides) {
		super(id);
		this.info_sys = info_sys;
		this.rides = rides;
	}

	@Override
	public void run() {

		while (info_sys.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// attempt to grab a call
		grabCall();

	}


	//grab a call
	private void grabCall() {

		ServiceCall call = null;

		double chance = Math.random();

		// check chance
		if (chance < 0.5) {
			if (!info_sys.deliveryCalls().isEmpty()) {
				call = info_sys.deliveryCalls().remove();
			}
		} else {
			if (!info_sys.taxiCalls().isEmpty()) {
				call = info_sys.taxiCalls().remove();
			}
		}
		
		// if managed to grab a call
		if (call != null) {
			makeRideFrom(call);
		}
	}
	
	
	// make a ride from a service call
	private void makeRideFrom(ServiceCall call) {
		// getting a car
		Vehicle v = findVehicle();
		
		// creating instance of ReadyRide
		ReadyRide ride = new ReadyRide(call, v);
		
		// adding ride to queue
		while (rides.isFull()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rides.add(ride);
		
	}

	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

}
