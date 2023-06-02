
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
			
			try {
				ServiceCall call = calls.extract();
				checkCall(call);
			} catch (InterruptedException e) {}
			
		}

	}


	// check a service call
	private void checkCall(ServiceCall call) {

		// check area
		if (area.equals(call.area())) {
			// find and remove vehicle
			findVehicle();

			// sleep
			

			// adding to IS
			IS.addCall(call);
			printCall(call);

		} else {
			// area doesn't match
			calls.insert(call);
		}
	}

	// find vehicle for a call
	private void findVehicle() {
		//TODO
	}


	// call announcement
	private void printCall(ServiceCall call) {
		System.out.println("New service call (id: " + call.id() + ") arrived and data inserted to database");
	}

}
