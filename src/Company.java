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
	private UnboundedBuffer<Vehicle> vehicles;
	private Vector<Customer> customers;
	private Vector<Employee> employees;

	private UnboundedBuffer<Request> requests, special_requests;
	private UnboundedBuffer<ServiceCall> calls;
	private BoundedBuffer<ReadyRide> rides;
	
	private Vector<String> request_text;	// for creating requests out of file


	// constructor
	public Company(int num_of_drivers, double car_officer_work_time) {

		// initialize lists
		createCompanyDataBase();
		createVehicleDataBase();


		// read request file
		request_text= readRequests();
		int num_of_requests = request_text.size();


		// initialize employees
		createEmployees(num_of_drivers, car_officer_work_time, num_of_requests);

	}

	
	// start the workday
	public void startWorkDay() {
		// start all the threads (requests and employees)
		startRequests();
		startEmployees();
	}
	

	// create employees and add them to thread list
	private void createEmployees(int num_of_drivers, double car_officer_work_time, int num_of_requests) {

		// manager
		manager = new Manager(special_requests, IS, customers, num_of_requests, employees, vehicles);

		// drivers
		for (int i = 1; i <= num_of_drivers; i++) {
			Driver d;
			char lisence;
			if (i % 2 == 0) {
				lisence = 'A';
			} else {
				lisence = 'B';
			}
			d = new Driver(i, lisence, rides, manager, vehicles);
			employees.add(d);
		}


		// clerks
		clerk = new Clerk[3];
		for (int i = 0; i < 3; i++) {
			clerk[i] = new Clerk(i+1, requests, special_requests, calls, customers, num_of_requests);
			employees.add(clerk[i]);
		}


		// schedulers
		scheduler = new Scheduler[2];
		for (int i = 0; i < 2; i++) {
			String area = (i == 1) ? "Tel Aviv" : "Jerusalem";
			scheduler[i] = new Scheduler(i+1, area, calls, IS, vehicles);
			employees.add(scheduler[i]);
		}


		// car officers
		car_officer = new CarOfficer[3];
		for (int i = 0; i < 3; i++) {
			car_officer[i] = new CarOfficer(i+1, IS, rides, car_officer_work_time);
			employees.add(car_officer[i]);
		}

	}


	// initialize information system and lists - including a thread list
	private void createCompanyDataBase() {

		IS = new InformationSystem();

		employees = new Vector<Employee>();	
		requests = new UnboundedBuffer<Request>();
		special_requests = new UnboundedBuffer<Request>();
		calls = new UnboundedBuffer<ServiceCall>();
		rides = new BoundedBuffer<ReadyRide>();
		customers = new Vector<Customer>();

	}


	// create new vehicle database
	private void createVehicleDataBase() {
		vehicles = new UnboundedBuffer<Vehicle>();
		int num_of_vehicles = (int)(Math.random() * 50) + 100;
		System.out.println("Number of vehicles: " + num_of_vehicles);
		for (int i = 1; i <= num_of_vehicles; i++) {
			vehicles.insert(createVehicle(i));
		}		
	}


	// start employee threads
	private void startEmployees() {
		manager.start();
		for (Employee e: employees) {
			e.start();
		}
	}


	// read the requests file
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


	// convert a line of text and an id to a request
	private Request convert2Request(String line, int id) {
		//make an array of the row element (seperated by tab)
		String[] row = line.split("\t");

		// get the elements to right variables
		int customer_id = Integer.parseInt(row[0]);
		String type = row[1];
		String area = row[2];
		double distance = Double.parseDouble(row[3]);
		double time = Double.parseDouble(row[4]);
		double arrival = Double.parseDouble(row[5]);

		Request request = new Request(id, customer_id, type, area, distance, arrival, time, requests);
		return request;
	}


	// turn the requests from the files to threads and start them
	private void startRequests() {
		int request_id = 1;
		for (String line: request_text) {
			Request request = convert2Request(line, request_id);
			Thread r = new Thread(request);
			r.start();
		}
	}


	// create a random vehicle
	private Vehicle createVehicle(int license_number) {

		int type = (int)(Math.random()*2);

		String[] models = {"Toyota", "Honda", "Audi", "Transformer"};
		String model = models[(int)(Math.random() * 4)];
		int year = (int)(Math.random() * 53) + 1970;

		if (type == 0) {
			return new Taxi(license_number, model, year);
		} else {
			int max_speed = (int)(Math.random()*200) + 10;
			return new Motorcycle(license_number, model, year, max_speed);
		}

	}

}
