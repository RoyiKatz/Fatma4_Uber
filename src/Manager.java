import java.util.Vector;

public class Manager extends Thread {

	private UnboundedBuffer<Request> requests;
	private InformationSystem IS;
	private Vector<Customer> customers;
	private Vector<Employee> employees;
	private int expected_calls, finished_drives;
	UnboundedBuffer<Vehicle> vehicles;


	// constructor
	public Manager(UnboundedBuffer<Request> requests, InformationSystem IS, Vector<Customer> customers,
			int expected_calls, Vector<Employee> employees, UnboundedBuffer<Vehicle> vehicles) {

		this.requests = requests;
		this.IS = IS;
		this.customers = customers;
		finished_drives = 0;
		this.expected_calls = expected_calls;
		this.employees = employees;
		this.vehicles = vehicles;
	}


	public void run() {

		while(finished_drives < expected_calls) {
			work();
		}

		endDay();
	}


	// end of day
	private synchronized void endDay() {
		
		// notify schedulers, drivers, car officers
		alertEveryoneThatTheDayIsOver();

		// print the total wage of the schedulers, car officers, drivers, average pay
		printEmployeeWages();

		// print the number of deliveries, taxi rides
		System.out.println("Number of deliveries: " + IS.numOfDeliveries());
		System.out.println("Number of taxi rides: " + IS.numOfTaxiRides());

		// print the most popular service area
		System.out.println("The most popular area is " + IS.mostPopularArea());

		// finish
		System.out.println("Manager finished");
	}


	// print employee salary stats
	private void printEmployeeWages() {
		double schedulers_wage = 0;
		double drivers_wage = 0;
		double car_officers_wage = 0;
		double clerks_wage = 0;
		
		// calculate employee wages
		for (Employee employee: employees) {
			switch (employee.getClass().getSimpleName()) {
			case "Clerk":
				clerks_wage += employee.wage();
				break;
			case "Scheduler":
				schedulers_wage += employee.wage();
				break;
			case "CarOfficer":
				car_officers_wage += employee.wage();
				break;
			case "Driver":
				drivers_wage += employee.wage();
				break;
			}
		}
		
		// printing wages
		System.out.println("Schedulers pay for the day: " + schedulers_wage);
		System.out.println("Car Officers pay for the day: " + car_officers_wage);
		System.out.println("Drivers pay for the day: " + drivers_wage);
		
		// calculate and print the average
		double avg = schedulers_wage + drivers_wage + car_officers_wage + clerks_wage;
		avg /= employees.size();
		System.out.println("The average employee salary is: " + avg);
		
	}


	// notify the employees the day is over
	private synchronized void alertEveryoneThatTheDayIsOver() {
		for (Employee employee: employees) {
			if (!(employee instanceof Clerk)) {
				employee.finishWorkDay();
				employee.interrupt();
			}
		}

	}


	// work logic
	private void work() {
		try {
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
			Vehicle v = findVehicle(call);

			// insert call to IS
			IS.addCall(new Ride(call, v));

			//print message
			printCall(call);

			//terminate request
			request.stop();
		} catch(InterruptedException e) {}
	}


	// find vehicle for a call
	private Vehicle findVehicle(ServiceCall call) {
		// get a vehicle
		Vehicle vehicle = null;
		try {
			System.out.println("Manager is waiting on a vehicle");
			vehicle = vehicles.extract();
			while (vehicle instanceof Motorcycle && call.type().equals("Taxi")) {
				vehicles.insert(vehicle);
				vehicle = vehicles.extract();
			}
			System.out.println("Manager grabbed vehicle " + vehicle.licenseNumber());
		} catch (InterruptedException e) {}

		return vehicle;
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
