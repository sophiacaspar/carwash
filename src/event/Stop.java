package event;

import simulator.Event;
import simulator.EventQueue;
import state.CarWashState;

/**
 * Defines a stop event and stops the process.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class Stop extends Event {

	CarWashState state;
	EventQueue eventQueue;

	/**
	 * The constructor. Defines the stop event.
	 * 
	 * @param stopTime
	 *            Saves the stop time to Event.
	 * @param state
	 *            Saves the state.
	 * @param eventQueue
	 *            saves the queue.
	 */
	public Stop(double stopTime, CarWashState state, EventQueue eventQueue) {
		super.startTime = stopTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	/**
	 * Determines the state, the Idle-, and Queue time and sets the start time for the stop event.
	 * 
	 * Updates the observers.
	 * 
	 * Clears the event queue.
	 */
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		state.setChanged();
		state.notifyObservers();

		eventQueue.clear();
	}

	@Override
	/**
	 * Returns the name of the event in string form.
	 */
	public String toString() {
		return "Stop";
	}

}
