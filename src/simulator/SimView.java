package simulator;

import java.util.Observable;
import java.util.Observer;

/**
 * Keeps track update classes.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 *
 */
public abstract class SimView implements Observer {

	@Override
	public abstract void update(Observable o, Object arg);

}
