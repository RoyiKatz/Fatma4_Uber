
public class UnboundedBuffer<T> extends Buffer<T> {

	private int limit;


	// constructors
	// default
	public UnboundedBuffer() {
		super();
		limit = 14;
	}

	// with given limit
	public UnboundedBuffer(int limit) {
		super();
		this.limit = limit;
	}


	// add element
	public boolean add(T item) {
		if (this.size() < limit) {
			super.add(item);
		}
		return false;
	}
	
}
