
public class BoundedBuffer<T> extends UnboundedBuffer<T> {
	
	private int limit;


	// constructors
	// default
	public BoundedBuffer() {
		super();
		limit = 14;
	}

	// with given limit
	public BoundedBuffer(int limit) {
		super();
		this.limit = limit;
	}
	
	
	// isFull
	public boolean isFull() {
		return this.size() == limit;
	}


	// add element
	public synchronized void insert(T item) {
		while (this.isFull()) {
			
			try {
				this.wait();
			} catch (InterruptedException e) {}
			
		}
		super.insert(item);
	}
	
	// extract element
	public synchronized T extcract() throws InterruptedException {
		T item = super.extract();
		this.notifyAll();
		return item;
	}

}
