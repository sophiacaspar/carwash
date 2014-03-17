package event;

import simulator.Event;
import simulator.EventQueue;
import state.CarWashState;

/**
 * Defines a start event and starts the process.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class Start extends Event {

	CarWashState state;
	EventQueue eventQueue;

	/**
	 * The constructor.
	 * 
	 * @param state
	 *            Saves the state.
	 * @param eventQueue
	 *            Saves the queue.
	 */
	public Start(CarWashState state, EventQueue eventQueue) {
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	/**
	 * Detirmines the state, detirmines the time.
	 * 
	 * Updates the observers.
	 */
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);

		state.setChanged();
		state.notifyObservers();

		Event firstArrival = new Arrive(state.getNextArrivalTime(), state,
				eventQueue); //Creates the first arrive object.
		eventQueue.add(firstArrival); //Adds the first arrive object.
	}

	@Override
	/**
	 * Returns the name of the event in string form.
	 */
	public String toString() {
		return "Start";
	}

}
