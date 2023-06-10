
public class Ride {

	protected ServiceCall order;
	protected Vehicle vehicle;
	protected int bonus_pay;
	
	
	// constructors
	public Ride(ServiceCall call, Vehicle v) {
		order = call;
		vehicle = v;
		bonus_pay = 0;
	}
	
	public Ride(ServiceCall call, Vehicle v, int bonus_pay) {
		this(call, v);
		this.bonus_pay = bonus_pay;
	}
	
	
	// getters
	public Vehicle vehicle() {
		return vehicle;
	}
	
	public ServiceCall details() {
		return order;
	}
	
	
	// bonus pay
	public int carOfficerBonus() {
		bonus_pay /= 2;
		return bonus_pay;
	}
	
	public int driverBonus() {
		int to_pay = bonus_pay;
		bonus_pay = 0;
		return to_pay;
	}
}
