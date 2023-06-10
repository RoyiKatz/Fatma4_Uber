
public abstract class Employee extends Thread {

	protected int id;
	protected double wage;
	protected boolean not_finished;
	
	
	// constructor
	public Employee(int id) {
		this.id = id;
		wage = 0;
		not_finished = true;
	}
	
	
	// getter
	public double wage() {
		return wage;
	}
	
	
	// run method
	public void run() {
		// while day is not finished
		while (not_finished) {
			work();
		}
		
		// when finished
		endDay();
	}

	protected abstract void work();
	
	protected abstract void endDay();
	protected abstract double calculatewage();

	
	
	// finish work day
	public synchronized void finishWorkDay() {
		not_finished = false;
		this.notify();
	}
}
