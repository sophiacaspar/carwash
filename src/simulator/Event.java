package simulator;

/**
 * Determines what the subclasses must include and sorts the queue depending on
 * the time.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public abstract class Event implements Comparable<Event> {

	public double startTime;

	@Override
	/**
	 * Sorting the queue depending on the time.
	 */
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
