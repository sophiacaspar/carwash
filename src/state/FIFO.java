package state;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Defines the car queue.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class FIFO {
	private ArrayList<Car> queue = new ArrayList<Car>(); // Creates a list for
															// the cars.

	/**
	 * Adds the car.
	 * 
	 * @param car
	 */
	public void add(Car car) {
		queue.add(car);
	}

	/**
	 * Returns the first car in the queue.
	 * 
	 * @return the first car.
	 * @throws NoSuchElementException
	 */
	public Car first() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return queue.get(0);
		}
	}

	/**
	 * If the queue is empty it returns true or false.
	 * 
	 * @return true or false if the queue is empty.
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Removes the first car in the queue.
	 * 
	 * @throws NoSuchElementException
	 */
	public void removeFirst() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			queue.remove(0);
		}
	}

	/**
	 * 
	 * @return the queue size.
	 */
	public int size() {
		return queue.size();
	}
}
