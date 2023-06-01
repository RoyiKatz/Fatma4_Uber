
public class Scheduler extends Employee implements Runnable {

	private String area;
	private UnboundedBuffer<ServiceCall> calls;
	private InformationSystem info_system;

	
	public Scheduler(int id, String area, UnboundedBuffer<ServiceCall> calls, InformationSystem info_system) {
		super(id);
		this.area = area;
		this.calls = calls;
		this.info_system = info_system;
	}


	@Override
	public void run() {
		
		// while there are no calls
		while(calls.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// there are calls
		ServiceCall call = calls.remove();
		checkCall(call);
		
	}
	
	
	// check a service call
	private void checkCall(ServiceCall call) {
		
		// check area
		if (area.equals(call.area())) {
			// find and remove vehicle
			findVehicle();
			
			// sleep
			
			
			// adding to IS
			info_system.addCall(call);
			printCall(call);
			
		} else {
			calls.add(call);
		}
	}
	
	// find vehicle for a call
	private void findVehicle() {
		//TODO
	}
	
	
	// call announcement
	private void printCall(ServiceCall call) {
		System.out.println("New service call arrived and data inserted to database");
		System.out.println("Call no. " + call.id());
	}
	
}
