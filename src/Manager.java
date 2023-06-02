import java.util.Vector;

public class Manager extends Thread {

	private UnboundedBuffer<Request> requests;
	private InformationSystem IS;
	private Vector<Customer> customers;
	private int expected_calls, finished_drives;


	// constructor
	public Manager(UnboundedBuffer<Request> requests, InformationSystem IS,
			Vector<Customer> customers, int expected_calls) {
		
		this.requests = requests;
		this.IS = IS;
		this.customers = customers;
		finished_drives = 0;
		this.expected_calls = expected_calls;
	}


	public void run() {

		while(finished_drives < expected_calls) {
			try {

				Request request = requests.extract();
				
				// sleep - 3 seconds
				Thread.sleep(3000);

				// make service call
				ServiceCall call = makeServiceCallFrom(request);

				// find a vehicle
				Vehicle v = findVehicle();

				// insert call to IS
				IS.addCall(call);

				//print message
				printCall(call);

				//terminate request

			} catch (InterruptedException e) {}

			// notify everybody to finish
		}
	}


	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		return null;
	}


	// make a service call from a request
	private ServiceCall makeServiceCallFrom(Request request) {

		// get variables
		Customer c = findCustomer(request.customerID());
		String service = request.type();
		String area = request.area();
		double distance = request.distance();

		//sleep
		try {
			Thread.sleep(request.time());
		} catch (InterruptedException e) {}


		// create service call
		return new ServiceCall(requests.getID(), c, service, area, distance);

	}

	// find a customer by id
	private Customer findCustomer(int id) {

		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).id() == id) {
				return customers.get(i);
			}
		}

		// if we didn't find the customer
		return null;

	}

	// call announcement
	private void printCall(ServiceCall call) {
		System.out.println("New Special Service Call (id: " + call.id() + ") Arrived, distance: " + call.distance());
	}
	
	
	// update a finished drive
	public void updateDrive() {
		finished_drives++;
	}


}
