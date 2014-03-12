package state;

import main.Main;
import random.ExponentialRandomStream;
import random.UniformRandomStream;
import simulator.Event;
import simulator.SimState;


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

	private double[] fastDist = new double[] { fastLow, fastHigh };
	private double[] slowDist = new double[] { slowLow, slowHigh };
	private double lambda = Main.lambda;
	private long seed = Main.seed;

	private double totalQueueTime;
	private double totalIdleTime;
	public int rejected;
	public int totalCars;

	private Car currentCar;
	public FIFO carQueue = new FIFO();

	private int maxCarQueueSize = Main.queueSize;
	private CarFactory carFactory = new CarFactory();

	private Event previousEvent;
	private Event currentEvent;

	public CarWashState(int totalFastWashers, int totalSlowWashers) {
		fastWashTime(fastDist[0], fastDist[1], seed);
		slowWashTime(slowDist[0], slowDist[1], seed);
		nextArrival(lambda, seed);
		this.totalFastWashers = totalFastWashers;
		this.totalSlowWashers = totalSlowWashers;
		availableFastWashers = totalFastWashers;
		availableSlowWashers = totalSlowWashers;
	}


	public Car makeCar() {
		return carFactory.makeCar();
	}

	public int amountAvailableWashers() {
		return availableFastWashers + availableSlowWashers;
	}

	public boolean carQueueIsFull() {
		return (carQueue.size() >= maxCarQueueSize);
	}


	public Car getCurrentCar() {
		return currentCar;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}


	public double[] getFastWasherDistribution() {
		return fastDist;
	}

	public double getFastWasherFinishTime() {
		return currentTime + fastRandom.next();
	}

	public double getLambda() {
		return lambda;
	}


	public int getMaxCarQueueSize() {
		return maxCarQueueSize;
	}


	public double getNextArrivalTime() {
		return currentTime + exRandom.next();
	}


	public long getSeed() {
		return seed;
	}

	public double[] getSlowWasherDistribution() {
		return slowDist;
	}

	public double getSlowWasherFinishTime() {
		return currentTime + slowRandom.next();
	}

	public int getTotalFastWashers() {
		return totalFastWashers;
	}

	public double getTotalIdleTime() {
		return totalIdleTime;
	}

	public double getTotalQueueTime() {
		return totalQueueTime;
	}

	public int getTotalSlowWashers() {
		return totalSlowWashers;
	}

	public boolean hasAvailableWashers() {
		return (availableFastWashers != 0 || availableSlowWashers != 0);
	}

	public double meanQueueingTime() {
		
		if(totalCars > 0){
			return totalQueueTime/totalCars;
		}
		return 0.00;
	}

	public void nextArrival(double lambda, long seed) {
		this.lambda = lambda;
		this.seed = seed;
		exRandom = new ExponentialRandomStream(lambda, seed);
	}


	@Override
	public void setChanged() {
		super.setChanged();
	}


	public void setCurrentCar(Car car) {
		currentCar = car;
	}


	public void setCurrentEvent(Event currentEvent) {
		previousEvent = this.currentEvent;
		this.currentEvent = currentEvent;
	}

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


	public void slowWashTime(double low, double high, long seed) {
		slowDist[0] = low;
		slowDist[1] = high;
		this.seed = seed;
		slowRandom = new UniformRandomStream(low, high, seed);
	}

	public void setTime(double time) {
		currentTime = time;
	}

	public void updateIdleTime() {
		if(currentEvent == null){
			currentEventTime = 0.0;
		}
		else{
			currentEventTime = currentEvent.startTime;
		}
		
		if (previousEvent == null){
			previousEventTime = 0.0;
		}	
		else{
			previousEventTime = previousEvent.startTime;
		}

		totalIdleTime = totalIdleTime + amountAvailableWashers() * (currentEventTime - previousEventTime);
	}


	public void updateQueueTime() {
		if(currentEvent == null){
			currentEventTime = 0.0;
		}
		else{
			currentEventTime = currentEvent.startTime;
		}
		
		if (previousEvent == null){
			previousEventTime = 0.0;
		}	
		else{
			previousEventTime = previousEvent.startTime;
		}

		totalQueueTime = totalQueueTime + carQueue.size() * Math.abs(currentEventTime - previousEventTime);
	}

}