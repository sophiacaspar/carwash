package simulator;


public abstract class Event implements Comparable<Event> {

	public double startTime;

	@Override
	public int compareTo(Event otherEvent) {
		if (this.startTime == otherEvent.startTime) {
			return 0;
		} else if (this.startTime < otherEvent.startTime) {
			return -1;
		} else {
			return 1;
		}
	}

	public abstract void execute();

	@Override
	public abstract String toString();
}
