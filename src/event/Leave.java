package event;

import simulator.Event;
import simulator.EventQueue;
import state.Car;
import state.CarWashState;

public class Leave extends Event {

	CarWashState state;
	EventQueue eventQueue;
	Car car;
	boolean fastWasher = false;
	double timeFinished;

	public Leave(double startTime, CarWashState state, EventQueue eventQueue, Car car, boolean fastWasher) {
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
		this.car = car;
		this.fastWasher = fastWasher;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setCurrentCar(car);

		state.setChanged();
		state.notifyObservers();

		if (state.carQueue.isEmpty()) {
			if (fastWasher) {
				state.availableFastWashers++;
			} else {
				state.availableSlowWashers++;
			}

		} 
		else {
			if(fastWasher){
				timeFinished = state.getFastWasherFinishTime();
			}
			else{
				timeFinished = state.getSlowWasherFinishTime();
			}

			Car carToWash = state.carQueue.first();
			state.carQueue.removeFirst();

			Leave leaveEvent = new Leave(timeFinished, state, eventQueue, carToWash, fastWasher);
			eventQueue.add(leaveEvent);
		}
	}

	@Override
	public String toString() {
		return "Leave";
	}
}
