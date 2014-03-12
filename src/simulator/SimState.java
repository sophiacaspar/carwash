package simulator;

import java.util.Observable;

/**
 * Creates observable variables.
 * 
 * @author emmacarlsson, sophiacaspar, malinroos
 *
 */
public abstract class SimState extends Observable {

	public double currentTime;
	public Event currentEvent;

}
