

public class CarOfficer extends Employee {

	private InformationSystem IS;
	private BoundedBuffer<ReadyRide> rides;
	private double work_time;

	public CarOfficer(int id, InformationSystem info_sys, BoundedBuffer<ReadyRide> rides, double work_time) {
		super(id);
		this.IS = info_sys;
		this.rides = rides;
		this.work_time = work_time;
	}



	// work logic
	protected void work() {
		grabCall();
	}


	// end of day
	protected void endDay() {
		System.out.println("Car Officer " + id + " finished");
	}


	//grab a call
	private void grabCall() {

		// choose a buffer
		String choice = chooseBuffer();

		if (not_finished) {
			// try to grab a call
			try {
				Ride call = IS.extract(choice);
				System.out.println(this.toString() + ", current pay - " + this.wage());
				System.out.println(call.details());
				makeRideFrom(call);
			} catch(InterruptedException e) {}
		}

	}


	// return whether the chosen buffer is empty and the other is not
	private boolean wrongChoice(String choice) {
		return IS.isEmpty(choice) && !IS.isEmpty();
	}

	// choose service calls queue from information system
	private String chooseBuffer(){
		double chance = Math.random();

		String choice = (chance < 0.5) ? "Delivery" : "Taxi";

		// if it's a wrong choice - sleep and choose again
		while (wrongChoice(choice)) {
			//sleep
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
			//choose again
			choice = chooseBuffer();
		}

		return choice;
	}


	// make a ride from a service call
	private void makeRideFrom(Ride call) {

		// sleep
		if (work_time > 0) {
			try {
				Thread.sleep((long)(work_time * 1000));
			} catch (InterruptedException e) {}
		}

		// creating instance of ReadyRide
		ReadyRide ride = new ReadyRide(call);
		
		// get payed
		getPayed(5);
		getPayed(call.carOfficerBonus());

		rides.insert(ride);
	}
	
	public String toString() {
		return "Car Officer " + id;
	}

}
