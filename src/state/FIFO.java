package state;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FIFO {
	private ArrayList<Car> queue = new ArrayList<Car>();

	public void add(Car car) {
		queue.add(car);
	}

	public Car first() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return queue.get(0);
		}
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public void removeFirst() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			queue.remove(0);
		}
	}

	public int size() {
		return queue.size();
	}
}
