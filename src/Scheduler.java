
public class Scheduler extends Employee implements Runnable {

	private String area;
	private UnboundedBuffer<ServiceCall> calls;

	
	public Scheduler(int id, String area, UnboundedBuffer<ServiceCall> calls) {
		super(id);
		this.area = area;
		this.calls = calls;
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
			findVehicle();
		} else {
			calls.add(call);
		}
	}
	
	// find vehicle for a call
	private void findVehicle() {
		//TODO
	}
	
}
