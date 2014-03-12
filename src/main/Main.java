package main;

import event.Start;
import event.Stop;
import simulator.EventQueue;
import simulator.Simulator;
import state.CarWashState;
import view.CarWashView;

public class Main {

	public static int fastMachines = 2;
	public static int slowMachines = 2;
	public static double fastLow = 2.8;
	public static double fastHigh = 4.6;
	public static double slowLow = 3.5;
	public static double slowHigh = 6.7;
	public static double lambda = 2.0;
	public static long seed = 1234;
	public static int queueSize = 5;
	public static int stopTime = 15;
	
	public static void main(String[] args) {

		EventQueue eventQueue = new EventQueue();

		CarWashState cwState = new CarWashState(fastMachines, slowMachines);
		cwState.fastWashTime(fastLow, fastHigh, seed);
		cwState.slowWashTime(slowLow, slowHigh, seed);
		cwState.nextArrival(lambda, seed);
		cwState.setMaxCarQueueSize(queueSize);

		eventQueue.add(new Start(cwState, eventQueue));
		eventQueue.add(new Stop(stopTime, cwState, eventQueue));

		CarWashView view = new CarWashView(cwState);
		cwState.addObserver(view);

		Simulator sim = new Simulator(eventQueue);
		sim.run();
	}
}