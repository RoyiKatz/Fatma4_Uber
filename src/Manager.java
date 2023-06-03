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
			work();
		}

		endDay();
	}


	private void endDay() {
		
		// notify schedulers, drivers, car officers
		
		// print the total wage of the schedulers, car officers, drivers
		
		// print the average employee wage
		
		// print the number of deliveries, taxi rides
		
		// print the most popular service area

		// finish
		System.out.println("Manager finished");
	}


	private void work() {
		try {
			// get request
			Request request = requests.extract();

			// sleep - 3 seconds
			Thread.sleep(3000);

			// make service call
			ServiceCall call = makeServiceCallFrom(request);
			System.out.println("Manager handling call " + call.id());

			// find a vehicle
			Vehicle v = findVehicle();

			// insert call to IS
			IS.addCall(call);

			//print message
			printCall(call);

			//terminate request
			request.stop();
		} catch (InterruptedException e) {}
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
		return new ServiceCall(request.id(), c, service, area, distance);

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


	// update rides whenever a driver finishes a ride
	public synchronized void updateRides() {
		finished_drives++;
		notify();
	}


}
