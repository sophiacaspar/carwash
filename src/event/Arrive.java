package event;

import simulator.Event;
import simulator.EventQueue;
import state.Car;
import state.CarWashState;

public class Arrive extends Event {

	CarWashState state;
	EventQueue eventQueue;
	private double timeFinished;

	public Arrive(double startTime, CarWashState state,
		EventQueue eventQueue) {
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
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
			if (state.availableFastWashers != 0) {
				fastWasher = true;
				state.availableFastWashers--;
			} 
			else {
				state.availableSlowWashers--;
			}
			
			if (fastWasher){
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

		Arrive arrivalEvent = new Arrive(state.getNextArrivalTime(), state, eventQueue);
		eventQueue.add(arrivalEvent);
	}

	@Override
	public String toString() {
		return "Arrive";
	}

}
