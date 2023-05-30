import java.util.Iterator;
import java.util.Vector;

public class UnboundedBuffer<T> implements Iterable<T>{

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
	
	
	// get elements
	public T get(int index) {
		return queue.get(index);
	}
	
	public T getFirst() {
		return queue.firstElement();
	}
	
	public T getLast() {
		return queue.lastElement();
	}
	
	
	// add element
	public boolean add(T item) {
		queue.add(item);
		return true;
	}
	
	// remove element
	public T remove() {
		T first = queue.get(0);
		queue.remove(0);
		return first;
	}


	@Override
	public Iterator<T> iterator() {
		return queue.iterator();
	}
	
}
