import java.util.Vector;

public class Clerk  extends Employee implements Runnable{

	private UnboundedBuffer<Request> requests, special_requests;
	private UnboundedBuffer<ServiceCall> calls;
	private Vector<Customer> customers;
	private int requests_left;

	public Clerk(int id, UnboundedBuffer<Request> requests, UnboundedBuffer<Request> special_requests
			, UnboundedBuffer<ServiceCall> calls, Vector<Customer> customers) {
		super(id);

		this.requests = requests;
		this.special_requests = special_requests;
		this.calls = calls;
		this.customers = customers;
		requests_left = 100;
	}

	@Override
	public void run() {

		// while not finished
		while (requests_left > 0) {
			//continue working
			work();
		}

		// finished the day - notify other clerks
	}


	// try to grab a request
	private void work() {
		try {

			Request request = requests.extract();
			checkRequest(request);

		} catch(InterruptedException e) {}

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
		ServiceCall call = new ServiceCall(calls.getID(), c, service, area, distance);

		// add call to queue
		calls.insert(call);

		// terminate request
		requests_left--;

		// get payed;
		wage += 4 ;
	}


	// send a request to the manager
	private void sendToManager(Request request) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		special_requests.insert(request);

	}
}
