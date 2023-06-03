
public class Scheduler extends Employee implements Runnable {

	private String area;
	private UnboundedBuffer<ServiceCall> calls;
	private InformationSystem IS;


	public Scheduler(int id, String area, UnboundedBuffer<ServiceCall> calls, InformationSystem IS) {
		super(id);
		this.area = area;
		this.calls = calls;
		this.IS = IS;
	}


	@Override
	public void run() {
		while(not_finished) {
			work();
		}
		
		System.out.println("Scheduler " + id + " finished");

	}
	
	protected void work() {
		try {
			ServiceCall call = calls.extract();
			checkCall(call);
		} catch (InterruptedException e) {}
	}


	// check a service call
	private void checkCall(ServiceCall call) {

		// check area
		if (area.equals(call.area())) {

			handleCall(call);

		} else {
			// area doesn't match
			calls.insert(call);
		}
	}

	private void handleCall(ServiceCall call) {
		
		System.out.println("Scheduler " + id + " handling call " + call.id() + "...");

		// find and remove vehicle
		Vehicle v = findVehicle();

		// sleep
		double time_to_sleep = v.calculateDrivingTime(call.distance()) * 25;
		try {
			Thread.sleep((long)time_to_sleep);
		} catch (InterruptedException e) {}

		// adding to IS
		IS.addCall(call);
		printCall(call);

	}


	// find vehicle for a call
	private Vehicle findVehicle() {
		//TODO
		return new Taxi(123, "toyota", 2000);
	}


	// call announcement
	private void printCall(ServiceCall call) {
		System.out.println("New service call (id: " + call.id() + ") arrived and data inserted to database");
	}

}
