package simulator;

import java.util.Observable;
import java.util.Observer;

public abstract class SimView implements Observer {

	@Override
	public abstract void update(Observable o, Object arg);

}
