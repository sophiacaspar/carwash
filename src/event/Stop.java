package event;

import simulator.Event;
import simulator.EventQueue;
import state.CarWashState;

public class Stop extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public Stop(double stopTime, CarWashState state, EventQueue eventQueue) {
		super.startTime = stopTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
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
	public String toString() {
		return "Stop";
	}

}
