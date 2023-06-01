
public class Request extends Call implements Runnable {

	private int customer_id;
	private double arrival_time, working_time;
	private UnboundedBuffer<Request> requests;
	
	
	// constructor
	public Request(int customer_id, String service_type, String service_area,
			double distance, double arrival, double working_time, UnboundedBuffer<Request> requests) {
		
		super(service_type, service_area, distance);

		this.customer_id = customer_id;
		arrival_time = arrival;
		this.working_time = working_time;
		this.requests = requests;
		
	}
	

	// getters
	public int customerID() {
		return customer_id;
	}
	
	public double time() {
		return working_time;
	}
	
	public long arrivalTime() {
		long time = (long)(arrival_time * 1000);
		return time;
	}
	
	
	// add request to queue
	private synchronized void addToQueue() {
		requests.add(this);
		requests.notifyAll();
	}

	
	@Override
	public void run() {
		// sleep
		try {
			Thread.sleep(arrivalTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// adding to request queue
		addToQueue();
		
		// wait for request to be processed
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
