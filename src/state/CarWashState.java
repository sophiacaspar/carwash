package state;

import main.Main;
import random.ExponentialRandomStream;
import random.UniformRandomStream;
import simulator.Event;
import simulator.SimState;

/**
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 * 
 */
public class CarWashState extends SimState {
	private int totalFastWashers = Main.fastMachines;
	private int totalSlowWashers = Main.slowMachines;
	public int availableFastWashers;
	public int availableSlowWashers;
	private double fastLow = Main.fastLow;
	private double fastHigh = Main.fastHigh;
	private double slowLow = Main.slowLow;
	private double slowHigh = Main.slowHigh;

	private double previousEventTime;
	private double currentEventTime;

	private UniformRandomStream fastRandom;
	private UniformRandomStream slowRandom;
	private ExponentialRandomStream exRandom;

	private double[] fastDist = new double[] { fastLow, fastHigh }; // List for
																	// the fast
																	// car wash.
	private double[] slowDist = new double[] { slowLow, slowHigh }; // List for
																	// the slow
																	// car wash.
	private double lambda = Main.lambda;
	private long seed = Main.seed;

	private double totalQueueTime;
	private double totalIdleTime;
	public int rejected;
	public int totalCars;

	private Car currentCar;
	public FIFO carQueue = new FIFO(); // the FIFO queue is created.

	private int maxCarQueueSize = Main.queueSize;
	private CarFactory carFactory = new CarFactory(); // We get the new car
														// factory.

	private Event previousEvent;
	private Event currentEvent;

	/**
	 * The constructor. Creates the washing time.
	 * 
	 * @param totalFastWashers
	 *            Number of fast washers.
	 * @param totalSlowWashers
	 *            Number of slow washers.
	 */
	public CarWashState(int totalFastWashers, int totalSlowWashers) {
		fastWashTime(fastDist[0], fastDist[1], seed); // Determines the fast
														// washing time.
		slowWashTime(slowDist[0], slowDist[1], seed); // Determines the slow
														// washing time.
		nextArrival(lambda, seed);
		this.totalFastWashers = totalFastWashers;
		this.totalSlowWashers = totalSlowWashers;
		availableFastWashers = totalFastWashers;
		availableSlowWashers = totalSlowWashers;
	}

	/**
	 * 
	 * @return the new car.
	 */
	public Car makeCar() {
		return carFactory.makeCar();
	}

	/**
	 * 
	 * @return total amount of available washers.
	 */
	public int amountAvailableWashers() {
		return availableFastWashers + availableSlowWashers;
	}

	/**
	 * 
	 * @return true or false if the car queue is full or not.
	 */
	public boolean carQueueIsFull() {
		return (carQueue.size() >= maxCarQueueSize);
	}

	/**
	 * 
	 * @return the current car.
	 */
	public Car getCurrentCar() {
		return currentCar;
	}

	/**
	 * 
	 * @return the current event.
	 */
	public Event getCurrentEvent() {
		return currentEvent;
	}

	/**
	 * 
	 * @return the new time for the fast washer to finish.
	 */
	public double getFastWasherFinishTime() {
		return currentTime + fastRandom.next();
	}

	/**
	 * 
	 * @return max queue size.
	 */
	public int getMaxCarQueueSize() {
		return maxCarQueueSize;
	}

	/**
	 * 
	 * @return the time for when the new car arrives.
	 */
	public double getNextArrivalTime() {
		return currentTime + exRandom.next();
	}

	/**
	 * 
	 * @return the new time for the slow washer to finish.
	 */
	public double getSlowWasherFinishTime() {
		return currentTime + slowRandom.next();
	}

	/**
	 * 
	 * @return the amount of fast washers.
	 */
	public int getTotalFastWashers() {
		return totalFastWashers;
	}

	/**
	 * 
	 * @return the total Idle timw.
	 */
	public double getTotalIdleTime() {
		return totalIdleTime;
	}

	/**
	 * 
	 * @return the total queue time.
	 */
	public double getTotalQueueTime() {
		return totalQueueTime;
	}

	/**
	 * 
	 * @return the amount of slow washers.
	 */
	public int getTotalSlowWashers() {
		return totalSlowWashers;
	}

	/**
	 * 
	 * @return true or false if there is a available washer.
	 */
	public boolean hasAvailableWashers() {
		return (availableFastWashers != 0 || availableSlowWashers != 0);
	}

	/**
	 * 
	 * @return the queue time.
	 */
	public double meanQueueingTime() {

		if (totalCars > 0) {
			return totalQueueTime / totalCars;
		}
		return 0.00;
	}

	/**
	 * Randomizes the new time for the new car.
	 * 
	 * @param lambda
	 * @param seed
	 */
	public void nextArrival(double lambda, long seed) {
		this.lambda = lambda;
		this.seed = seed;
		exRandom = new ExponentialRandomStream(lambda, seed);
	}

	@Override
	/**
	 * The change is made in simState as well.
	 */
	public void setChanged() {
		super.setChanged();
	}

	/**
	 * 
	 * @param car
	 *            Sets the current car.
	 */
	public void setCurrentCar(Car car) {
		currentCar = car;
	}

	/**
	 * The event keeps being the event.
	 * 
	 * @param currentEvent
	 *            The event.
	 */
	public void setCurrentEvent(Event currentEvent) {
		previousEvent = this.currentEvent;
		this.currentEvent = currentEvent;
	}

	/**
	 * The time for the fast washer is set.
	 * 
	 * @param low
	 * @param high
	 * @param seed
	 */
	public void fastWashTime(double low, double high, long seed) {
		fastDist[0] = low;
		fastDist[1] = high;
		this.seed = seed;
		fastRandom = new UniformRandomStream(low, high, seed);
	}

	/**
	 * The maximum allowed length of the car queue.
	 * 
	 * @param maxCarQueueSize
	 */
	public void setMaxCarQueueSize(int maxCarQueueSize) {
		this.maxCarQueueSize = maxCarQueueSize;
	}

	/**
	 * The time for the slow washer is set.
	 * 
	 * @param low
	 * @param high
	 * @param seed
	 */
	public void slowWashTime(double low, double high, long seed) {
		slowDist[0] = low;
		slowDist[1] = high;
		this.seed = seed;
		slowRandom = new UniformRandomStream(low, high, seed);
	}

	/**
	 * Updates the time.
	 * 
	 * @param time
	 *            The current time.
	 */
	public void setTime(double time) {
		currentTime = time;
	}

	/**
	 * Sets the current and previous event time and calculates the total idle
	 * time.
	 */
	public void updateIdleTime() {
		if (currentEvent == null) {
			currentEventTime = 0.0;
		} else {
			currentEventTime = currentEvent.startTime;
		}

		if (previousEvent == null) {
			previousEventTime = 0.0;
		} else {
			previousEventTime = previousEvent.startTime;
		}

		totalIdleTime = totalIdleTime + amountAvailableWashers()
				* (currentEventTime - previousEventTime);
	}

	/**
	 * Sets the current and previous event time and calculates the total queue
	 * time.
	 */
	public void updateQueueTime() {
		if (currentEvent == null) {
			currentEventTime = 0.0;
		} else {
			currentEventTime = currentEvent.startTime;
		}

		if (previousEvent == null) {
			previousEventTime = 0.0;
		} else {
			previousEventTime = previousEvent.startTime;
		}

		totalQueueTime = totalQueueTime + carQueue.size()
				* Math.abs(currentEventTime - previousEventTime);
	}

}
