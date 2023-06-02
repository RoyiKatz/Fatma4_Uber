import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Company {

	private InformationSystem IS;
	private Manager manager;
	private Clerk[] clerk;
	private Scheduler[] scheduler;
	private CarOfficer[] car_officer;
	private Vector<Driver> drivers;
	private Vector<Vehicle> vehicles;
	private Vector<Customer> customers;

	private UnboundedBuffer<Request> requests, special_requests;
	private UnboundedBuffer<ServiceCall> calls;
	private BoundedBuffer<ReadyRide> rides;

	private Vector<Thread> t;

	public Company() {
		IS = new InformationSystem();

		// group all the threads together
		t = new Vector<Thread>();

		requests = new UnboundedBuffer<Request>();
		special_requests = new UnboundedBuffer<Request>();
		calls = new UnboundedBuffer<ServiceCall>();
		rides = new BoundedBuffer<ReadyRide>();
		vehicles = new Vector<Vehicle>();
		customers = new Vector<Customer>();

		drivers = new Vector<Driver>();
		for (int i = 1; i < 5; i++) {
			Driver d;
			if (i % 2 == 0) {
				d = new Driver(i, 'A', rides);
			} else {
				d = new Driver(i, 'B', rides);
			}
			drivers.add(d);
			t.add(new Thread(d));
		}

		clerk = new Clerk[3];
		for (int i = 0; i < 3; i++) {
			clerk[i] = new Clerk(i+1, requests, special_requests, calls, customers);
			t.add(new Thread(clerk[i]));
		}

		scheduler = new Scheduler[2];
		for (int i = 0; i < 2; i++) {
			String area = (i == 1) ? "Tel Aviv" : "Jerusalem";
			scheduler[i] = new Scheduler(i+1, area, calls, IS);
			t.add(new Thread(scheduler[i]));
		}

		car_officer = new CarOfficer[3];
		for (int i = 0; i < 3; i++) {
			car_officer[i] = new CarOfficer(i+1, IS, rides);
			t.add(new Thread(car_officer[i]));
		}

		manager = new Manager(special_requests, IS);
		t.add(manager);

	}



	public void start() {
		for (Thread tr : t) {
			tr.start();
		}
		
		startRequests();
	}


	private Vector<String> readRequests() {
		String address = "src\\Data\\Requests.txt";	//file address
		BufferedReader reader = null;
		String line;
		Vector<String> text = new Vector<String>();

		try {
			//get file text
			reader = new BufferedReader(new FileReader(address));

			//skip the header row
			reader.readLine();

			//copy
			while ((line = reader.readLine()) != null) {
				//make an array of the row element (seperated by tab)
				text.add(line);

			}

			//close file
			reader.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return text;

	}

	private void startRequests() {
		Vector<String> req_txt = readRequests();
		for (String line: req_txt) {
			//make an array of the row element (seperated by tab)
			String[] row = line.split("\t");

			// get the elements to right variables
			int customer_id = Integer.parseInt(row[0]);
			String type = row[1];
			String area = row[2];
			double distance = Double.parseDouble(row[3]);
			double time = Double.parseDouble(row[4]);
			double arrival = Double.parseDouble(row[5]);

			Request request = new Request(customer_id, type, area, distance, arrival, time, requests);
			Thread r = new Thread(request);
			r.start();
		}
	}
}
