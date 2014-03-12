package simulator;

public class Simulator {

	private EventQueue eventQueue;

	public Simulator(EventQueue eventQueue) {
		setEventQueue(eventQueue);
	}

	public void run() {
		do {
			Event event = eventQueue.first();
			eventQueue.removeFirst();
			event.execute();

		} while (!eventQueue.isEmpty());
	}

	public void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

}

