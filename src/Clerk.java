import java.util.Vector;

public class Clerk  extends Employee implements Runnable{

	private UnboundedBuffer<Request> requests;
	private UnboundedBuffer<Request> special_requests;
	
	public Clerk(int id, UnboundedBuffer<Request> requests, UnboundedBuffer<Request> special_requests) {
		super(id, 4);
		this.requests = requests;
		this.special_requests = special_requests;
	}

	@Override
	public void run() {

		// while there's no requests
		
		// wait
		
		// if there's a request, try to grab it
		 
		
	}
	
	
	// check a request
	private void checkRequest(Request request) {
		
		//remove from request queue
		requests.remove();
		
		// check customer
		Customer c = findCustomer(request.customerID(), null);
		if (c == null) {
			// create a new customer and add to company
			c = new Customer(request.customerID());
		}
		
		// check distance
		if (request.distance() < 100) {
			makeServiceCallFrom(request);
		} else {
			// move request to manager
			special_requests.add(request);
		}
	}
	
	
	// find a customer by id
	private Customer findCustomer(int id, Vector<Customer> customers) {
		
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
		Customer c = findCustomer(request.customerID(), null);
		String service = request.serviceType();
		String area = request.area();
		double distance = request.distance();
		
		ServiceCall call = new ServiceCall(0 /*change*/, c, service, service, distance);
		
		// add call to queue
		
		// notify to request it's finished
	}

	
}
