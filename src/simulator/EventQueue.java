package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * This class adds and removes event from the queue. The queue.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class EventQueue {

	private ArrayList<Event> eventQueue = new ArrayList<Event>();

	/**
	 * Event is added and sorted.
	 * 
	 * @param event
	 *            The event.
	 */
	public void add(Event event) {
		eventQueue.add(event);
		Collections.sort(eventQueue); // Sorts the queue.
	}

	/**
	 * Clears the queue.
	 */
	public void clear() {
		eventQueue.clear();
	}

	/**
	 * Returns the first event in the queue.
	 * 
	 * @return first event.
	 * @throws NoSuchElementException
	 */
	public Event first() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return eventQueue.get(0);
		}
	}

	/**
	 * If the queue is empty.
	 * 
	 * @return true or false
	 */
	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}

	/**
	 * Removes the first event in the queue.
	 * 
	 * @throws NoSuchElementException
	 */
	public void removeFirst() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			eventQueue.remove(0);
		}
	}

	/**
	 * The queue size.
	 * 
	 * @return the size.
	 */
	public int size() {
		return eventQueue.size();
	}
}
