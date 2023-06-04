import java.util.Vector;

public class Manager extends Thread {

	private UnboundedBuffer<Request> requests;
	private InformationSystem IS;
	private Vector<Customer> customers;
	private Vector<Employee> employees;
	private int expected_calls, finished_drives;


	// constructor
	public Manager(UnboundedBuffer<Request> requests, InformationSystem IS,
			Vector<Customer> customers, int expected_calls, Vector<Employee> employees) {

		this.requests = requests;
		this.IS = IS;
		this.customers = customers;
		finished_drives = 0;
		this.expected_calls = expected_calls;
		this.employees = employees;
	}


	public void run() {

		while(finished_drives < expected_calls) {
			work();
		}

		endDay();
	}


	private synchronized void endDay() {

		// notify schedulers, drivers, car officers
		alertEveryoneThatTheDayIsOver();

		// print the total wage of the schedulers, car officers, drivers

		// print the average employee wage

		// print the number of deliveries, taxi rides
		System.out.println("Number of deliveries: " + IS.numOfDeliveries());
		System.out.println("Number of taxi rides: " + IS.numOfTaxiRides());

		// print the most popular service area
		System.out.println("The most popular area is " + IS.mostPopularArea());

		// finish
		System.out.println("Manager finished");
	}


	private synchronized void alertEveryoneThatTheDayIsOver() {
		for (Employee employee: employees) {
			if (!(employee instanceof Clerk)) {
				employee.finishWorkDay();
				employee.interrupt();
			}
		}

	}


	private void work() {
		try {
			System.out.println("manager waiting for request...");
			// try to grab a request
			Request request = requests.extract();
			
			// condition to end the day
			if(request == null) {
				return;
			}
			
			// sleep - 3 seconds
			Thread.sleep(3000);

			// make service call
			ServiceCall call = makeServiceCallFrom(request);
			System.out.println("manager handling call " + call.id());

			// find a vehicle
			Vehicle v = findVehicle();

			// insert call to IS
			IS.addCall(new Ride(call, v));

			//print message
			printCall(call);

			//terminate request
			request.stop();
			System.out.println("manager terminating request " + request.id());
		} catch(InterruptedException e) {}
	}


	private Vehicle findVehicle() {
		// TODO Auto-generated method stub
		return new Taxi(123, "toyota", 2000);
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
		// update the number of finished drives
		finished_drives++;
		System.out.println("Drives over: " + finished_drives);
		
		// trigger the requests buffer to notify the manager
		requests.insert(null);
	}


}
