package event;

import simulator.Event;
import simulator.EventQueue;
import state.Car;
import state.CarWashState;

/**
 * Defines an leave event and creates new events.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class Leave extends Event {

	CarWashState state;
	EventQueue eventQueue;
	Car car;
	boolean fastWasher = false;
	double timeFinished;

	/**
	 * The constructor. (Defines a leave event).
	 * 
	 * @param startTime
	 *            Saves the start time to event.
	 * @param state
	 *            Saves the state.
	 * @param eventQueue
	 *            Saves the queue.
	 * @param car
	 *            Saves the car that will leave depending on Arrive.
	 * @param fastWasher
	 *            Saves true or false depending on Arrive.
	 */
	public Leave(double startTime, CarWashState state, EventQueue eventQueue,
			Car car, boolean fastWasher) {
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
		this.car = car;
		this.fastWasher = fastWasher;
	}

	@Override
	/**
	 * Determines state, time and car.
	 * 
	 * Updates Idle-, and Queue-time.
	 * 
	 * Updates the observers.
	 */
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setCurrentCar(car);

		state.setChanged();
		state.notifyObservers();

		if (state.carQueue.isEmpty()) { //If the queue is empty...
			if (fastWasher) { //If the washer has been true or false...
				state.availableFastWashers++;
			} else {
				state.availableSlowWashers++;
			}

		} else {
			if (fastWasher) {
				timeFinished = state.getFastWasherFinishTime(); //The finish time.
			} else {
				timeFinished = state.getSlowWasherFinishTime(); //The finish time.
			}

			Car carToWash = state.carQueue.first(); //Calls the next car in queue.
			state.carQueue.removeFirst(); //Removes the car.

			Leave leaveEvent = new Leave(timeFinished, state, eventQueue,
					carToWash, fastWasher); //Creates the leave event for the new car.
			eventQueue.add(leaveEvent); //Adds the leave event to the queue.
		}
	}

	@Override
	/**
	 * Returns the name of the event in string form.
	 */
	public String toString() {
		return "Leave";
	}
}
