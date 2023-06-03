import java.util.Vector;


public class UnboundedBuffer<T>{

	protected Vector<T> queue;


	// constructor
	public UnboundedBuffer(){
		queue = new Vector<T>();
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
		this.notifyAll();
	}

	// extract element
	public synchronized T extract() throws InterruptedException {
		while (queue.isEmpty()) {
			this.wait();
		}
		return queue.remove(0);
	}


}
