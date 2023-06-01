
public abstract class Employee {

	protected int id;
	protected int wage;
	
	
	// constructor
	public Employee(int id) {
		this.id = id;
		wage = 0;
	}
	
	
	// getter
	public int wage() {
		return wage;
	}
	
}
