
public class Customer {

	private int id;
	
	
	// constructor
	public Customer(int id) {
		this.id = id;
	}
	
	
	// getters
	public int id() {
		return id;
	}
	
	
	// give rating
	public int giveRating() {	
		return (int)(Math.random() * 10) + 1;
	}
	
	
	// return the payment for a ride
	public double pay(double time, double Fare) {
		return 2 * time + Fare ;
	}
	
}
