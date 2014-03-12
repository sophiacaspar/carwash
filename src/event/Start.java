package event;

import simulator.Event;
import simulator.EventQueue;
import state.CarWashState;

public class Start extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public Start(CarWashState state, EventQueue eventQueue) {
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);

		state.setChanged();
		state.notifyObservers();

		Event firstArrival = new Arrive(state.getNextArrivalTime(), state, eventQueue);
		eventQueue.add(firstArrival);
	}

	@Override
	public String toString() {
		return "Start";
	}

}
