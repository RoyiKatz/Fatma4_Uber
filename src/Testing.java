import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Testing {

	
	public static Vector<String> readRequests() {
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
	
	
	public static void main(String[] args) {

		// initializing lists
		UnboundedBuffer<Request> requests = new UnboundedBuffer<Request>();
		UnboundedBuffer<Request> special_requests = new UnboundedBuffer<Request>();
		UnboundedBuffer<ServiceCall> calls = new UnboundedBuffer<ServiceCall>();
		Vector<Customer> customers = new Vector<Customer>();
		
		//initializing employees
		Clerk c1 = new Clerk(0, requests, special_requests, calls, customers);
		Thread clerk1 = new Thread(c1);
		clerk1.start();
		
		//reading requests
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
