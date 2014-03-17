package event;

import simulator.Event;
import simulator.EventQueue;
import state.Car;
import state.CarWashState;

/**
 * Defines a arrive event and creates new events.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class Arrive extends Event {

	CarWashState state;
	EventQueue eventQueue;
	private double timeFinished;

	/**
	 * The constructor.
	 * 
	 * @param startTime
	 *            Saves the start time to Event.
	 * 
	 * @param state
	 *            Saves the state.
	 * 
	 * @param eventQueue
	 *            Saves the queue.
	 */
	public Arrive(double startTime, CarWashState state, EventQueue eventQueue) {
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	/**
	 * The event is this (arrive), the current event gets a start time, updates the Idle-, and queue-time.
	 * 
	 * Makes the car and sets the car.
	 * 
	 * Updates the observers.
	 * 
	 */
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		Car arrivedCar = state.makeCar();
		state.setCurrentCar(arrivedCar);

		state.setChanged();
		state.notifyObservers();

		if (state.carQueueIsFull()) {
			state.rejected++;
		} else {
			state.carQueue.add(arrivedCar);
			state.totalCars++;
		}

		if (state.hasAvailableWashers()) {
			boolean fastWasher = false;
			if (state.availableFastWashers != 0) { //If there is a fast washer...
				fastWasher = true;
				state.availableFastWashers--;
			} else {
				state.availableSlowWashers--;
			}

			if (fastWasher) { //If there is a fast washer...
				timeFinished = state.getFastWasherFinishTime(); //we get the fast washer finish time.
			} else {
				timeFinished = state.getSlowWasherFinishTime(); //we get the slow washer finish time.
			}

			Car carToWash = state.carQueue.first(); //The first car in the queue is washed.
			state.carQueue.removeFirst(); //Removes.

			Leave leaveEvent = new Leave(timeFinished, state, eventQueue,
					carToWash, fastWasher);
			eventQueue.add(leaveEvent); //Creates the leave event for the current car.
		}

		Arrive arrivalEvent = new Arrive(state.getNextArrivalTime(), state,
				eventQueue); //Gets the next arrival time and loops back to the beginning.
		eventQueue.add(arrivalEvent); //Adds the event to the queue.
	}

	@Override
	/**
	 * Returns the name of the event in string form.
	 */
	public String toString() {
		return "Arrive";
	}

}
