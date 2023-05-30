
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
	public boolean add(T item) {
		if (this.size() < limit) {
			super.add(item);
		}
		return false;
	}
}
