import java.util.Vector;

public class Clerk  extends Employee{

	private UnboundedBuffer<Request> requests, special_requests;
	private UnboundedBuffer<ServiceCall> calls;
	private Vector<Customer> customers;
	private int expected_requests;
	private static int requests_arrived;

	public Clerk(int id, UnboundedBuffer<Request> requests, UnboundedBuffer<Request> special_requests
			, UnboundedBuffer<ServiceCall> calls, Vector<Customer> customers, int expected_requests) {
		super(id);

		this.requests = requests;
		this.special_requests = special_requests;
		this.calls = calls;
		this.customers = customers;
		this.expected_requests = expected_requests;
        
	}


	// work logic
	protected void work() {
		try {

			// grab a request and count it
			Request request = requests.extract();	
			requests_arrived++;
			
			// check the request
			checkRequest(request);

			// if this is the last request
			if (requests_arrived == expected_requests) {
				finishWorkDay();
			}

		} catch(InterruptedException e) {}

	}
	
	
	// end the day
	protected void endDay() {
		// finished the day - notify other clerks
		System.out.println("clerk " + id + " finished");
		notifyAllClerks();
	}


	// alert other clerks the day is over
	private synchronized void notifyAllClerks() {
		notifyAll();
	}


	// check a request
	private void checkRequest(Request request) {

		// if the customer doesn't exist
		if (findCustomer(request.customerID()) == null) {
			// create a new customer and add to company
			createCustomerFrom(request.customerID());
		}

		// check distance
		if (request.distance() < 100) {

			makeServiceCallFrom(request);

		} else {

			// move request to manager
			sendToManager(request);

		}

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

	// create a customer from a given id
	private void createCustomerFrom(int id) {
		Customer c = new Customer(id);
		customers.add(c);
	}


	// make a service call from a request
	private void makeServiceCallFrom(Request request) {

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
		ServiceCall call = new ServiceCall(request.id(), c, service, area, distance);

		// add call to queue
		calls.insert(call);

		// terminate request
		request.stop();

		// get payed;
		getPayed(4);
	}
	     

	// send a request to the manager
	private void sendToManager(Request request) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		special_requests.insert(request);

	}


			
	
}
