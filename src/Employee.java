
public abstract class Employee {

	protected int id;
	protected int wage;
	protected boolean not_finished;
	
	
	// constructor
	public Employee(int id) {
		this.id = id;
		wage = 0;
		not_finished = true;
	}
	
	
	// getter
	public int wage() {
		return wage;
	}
	
	
	protected abstract void work();
	
	
	// finish work day
	public synchronized void finishWorkDay() {
		not_finished = false;
		this.notify();
	}
}
