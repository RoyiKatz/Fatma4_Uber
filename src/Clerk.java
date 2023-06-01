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
	}

	@Override
	public void run() {
		
		// while not finished
		while (requests_left > 0) {
			//continue working
			work();
		}
		
		// finished the day - notify other clerks
		this.notifyAll();;

	}
	
	
	// try to grab a request
	private void work() {
		// while there's no requests
				while (requests.isEmpty()) {
					// wait
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// if there's a request, try to grab it
				Request request = requests.remove();
				checkRequest(request);
	}


	// check a request
	private void checkRequest(Request request) {

		// check customer
		Customer c = findCustomer(request.customerID());
		if (c == null) {
			// create a new customer and add to company
			c = new Customer(request.customerID());
			customers.add(c);
		}

		// check distance
		if (request.distance() < 100) {
			makeServiceCallFrom(request);
		} else {
			// move request to manager
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			special_requests.add(request);
		}
	}


	// find a customer by id
	private Customer findCustomer(int id) {

		for (Customer customer : customers) {
			if (customer.id() == id) {
				return customer;
			}
		}

		// if we didn't find the customer
		return null;

	}

	// make a service call from a request
	private void makeServiceCallFrom(Request request) {
		Customer c = findCustomer(request.customerID());
		String service = request.type();
		String area = request.area();
		double distance = request.distance();

		ServiceCall call = new ServiceCall(0 /*change*/, c, service, area, distance);
		
		//sleep
		

		// add call to queue
		calls.add(call);
		// notify to request it's finished
		request.notify();
		requests_left--;
		
		// get payed;
		wage +=4 ;
	}


}
