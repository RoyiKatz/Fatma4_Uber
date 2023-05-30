import java.util.Vector;

public class Clerk  extends Employee implements Runnable{

	private UnboundedBuffer<Request> calls; // don't konw if requests or service calls
	
	public Clerk(int id) {
		super(id, 4);
	}

	@Override
	public void run() {

		// while there's no requests
		
		// wait
		
		// if there's a request, try to grab it
		 
		
	}
	
	
	// check a request
	private void checkRequest(Request request) {
		
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
