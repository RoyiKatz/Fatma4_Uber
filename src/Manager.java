
public class Manager extends Thread {

	private UnboundedBuffer<Request> requests;
	private InformationSystem IS;
	boolean day_not_over =  true;		// for tests
	
	
	// constructor
	public Manager(UnboundedBuffer<Request> requests, InformationSystem IS) {
		this.requests = requests;
		this.IS = IS;
	}
	
	
	public void run() {
		
		while(day_not_over) {
			try {
				
				Request request = requests.extract();
				
				// make service call
				ServiceCall call = makeServiceCallFrom(request);
				
				// find a vehicle
				Vehicle v = findVehicle();
				
				// sleep - 3 seconds
				Thread.sleep(3000);
				
				// insert call to IS
				IS.addCall(call);
				
				//print message
				
				//terminate request
				
			} catch (InterruptedException e) {}
			
		}
	}


	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		return null;
	}


	private ServiceCall makeServiceCallFrom(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
