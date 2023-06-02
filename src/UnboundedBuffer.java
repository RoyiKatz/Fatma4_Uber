import java.util.Vector;


public class UnboundedBuffer<T>{

	protected Vector<T> queue;
	protected int last_id; 		//to generate unique id


	// constructor
	public UnboundedBuffer(){
		queue = new Vector<T>();
		last_id = 1;
	}


	// size
	public int size() {
		return queue.size();
	}

	// isEmpty
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	// add element
	public synchronized void insert(T item) {
		queue.add(item);
		last_id++;
		this.notifyAll();
	}

	// extract element
	public synchronized T extract() throws InterruptedException {
		while (queue.isEmpty()) {
			this.wait();
		}
		return queue.remove(0);
	}
	
	// generate unique id
	public synchronized int getID() {
		return last_id;
	}

}
