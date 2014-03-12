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

	public void add(Event event) {
		eventQueue.add(event);
		Collections.sort(eventQueue); //Sorts the queue.
	}

	public void clear() {
		eventQueue.clear();
	}

	public Event first() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return eventQueue.get(0);
		}
	}

	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}

	public void removeFirst() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			eventQueue.remove(0);
		}
	}

	public int size() {
		return eventQueue.size();
	}
}
