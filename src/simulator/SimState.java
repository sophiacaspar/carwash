package simulator;

import java.util.Observable;

public abstract class SimState extends Observable {

	public double currentTime;
	public Event currentEvent;

}
