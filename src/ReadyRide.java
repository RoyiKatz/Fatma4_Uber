
public class ReadyRide extends Ride {

	
	// constructors
	public ReadyRide(ServiceCall call, Vehicle v) {
		super(call, v);
	}
	
	public ReadyRide(Ride r) {
		super(r.details(), r.vehicle());
	}
	
}
