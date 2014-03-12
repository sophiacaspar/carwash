package view;

import java.util.Observable;

import event.Start;
import event.Stop;
import simulator.SimView;
import state.CarWashState;

public class CarWashView extends SimView {

	private CarWashState state;

	public CarWashView(CarWashState state) {
		setState(state);
	}

	public void setState(CarWashState state) {
		this.state = state;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (state.getCurrentEvent() instanceof Start) {
			System.out.println(String.format("Fast machines: %s", state.getTotalFastWashers()));
			System.out.println(String.format("Slow machines: %s", state.getTotalSlowWashers()));
			System.out.println(String.format("Fast distribution: (%s, %s)", state.getFastWasherDistribution()[0], state.getFastWasherDistribution()[1]));
			System.out.println(String.format("Slow distribution: (%s, %s)", state.getSlowWasherDistribution()[0], state.getSlowWasherDistribution()[1]));
			System.out.println(String.format("Exponential distribution with lambda = %s", state.getLambda()));
			System.out.println(String.format("Seed = %s", state.getSeed()));
			System.out.println(String.format("Max Queue size: %s",state.getMaxCarQueueSize()));

			System.out.println("----------------------------------------");

			System.out.println("Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected");

			String reportLine = String.format("%.2f\t%s\t%s\t-\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent(),
					state.getTotalIdleTime(), state.getTotalQueueTime(),
					state.carQueue.size(), state.rejected);
			System.out.println(reportLine);

		} else if (state.getCurrentEvent() instanceof Stop) {

			String reportLine = String.format("%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent(),
					state.getTotalIdleTime(), state.getTotalQueueTime(),
					state.carQueue.size(), state.rejected);
			System.out.println(reportLine);

			System.out.println("----------------------------------------");

			System.out.println(String.format("Total idle machine time: %.2f", state.getTotalIdleTime()));
			System.out.println(String.format("Total queueing time: %.2f",state.getTotalQueueTime()));
			System.out.println(String.format("Mean queueing time: %.2f", state.meanQueueingTime()));
			System.out.println(String.format("Rejected cars: %s", state.rejected));
			System.out.println();

		} else {

			// Current state is either the arrival or leaving of a car.

			String reportLine = String.format("%.2f\t%s\t%s\t%s\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentCar().id,
					state.getCurrentEvent(), state.getTotalIdleTime(),
					state.getTotalQueueTime(), state.carQueue.size(),
					state.rejected);
			System.out.println(reportLine);
		}
	}
}
