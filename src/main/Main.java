package main;

/**
 * This program simulates a car wash.
 */

import event.Start;
import event.Stop;
import simulator.EventQueue;
import simulator.Simulator;
import state.CarWashState;
import view.CarWashView;

/**
 * Here some variables are set which determine qualities about the car wash.
 */
public class Main {

	public static int fastMachines = 2;
	public static int slowMachines = 4;
	public static double fastLow = 2.8;
	public static double fastHigh = 5.6;
	public static double slowLow = 4.5;
	public static double slowHigh = 6.7;
	public static double lambda = 1.5;
	public static long seed = 1234;
	public static int queueSize = 7;
	public static int stopTime = 15;

	/**
	 * Creates a new eventQueue, creates a new cwState and starts the simulation
	 * which will run until stopTime is reached.
	 * 
	 * @param args
	 */
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
