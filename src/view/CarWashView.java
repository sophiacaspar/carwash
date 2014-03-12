package view;

import java.util.Observable;

import main.Main;
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
			System.out.println(startOutput());
			
		} else if (state.getCurrentEvent() instanceof Stop) {
			System.out.println(stopOutput());

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
	public String startOutput(){
		String eol = System.getProperty("line.separator");
		String startMessage = "Fast machines: " + Main.fastMachines + eol +
				"Slow machines: " + Main.slowMachines + eol +
				"Fast distribution: (" + Main.fastLow + ", " + Main.fastHigh + ")" + eol +
				"Slow distribution: (" + Main.slowLow + ", " + Main.slowHigh + ")" + eol +
				"Exponential distribution with lambda = " + Main.lambda + eol +
				"Seed = " + Main.seed + eol+
				"Max Queue size: " + Main.queueSize + eol +
				"----------------------------------------" + eol+
				"Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected" + eol;
		
		String reportLine = String.format("%.2f\t%s\t%s\t-\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, state.getCurrentEvent(),
				state.getTotalIdleTime(), state.getTotalQueueTime(),
				state.carQueue.size(), state.rejected);
		return startMessage + reportLine;
	}
	
	public String stopOutput(){
		String eol = System.getProperty("line.separator");
		String reportLine = String.format("%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, state.getCurrentEvent(),
				state.getTotalIdleTime(), state.getTotalQueueTime(),
				state.carQueue.size(), state.rejected);
		
		String stopMessage = eol + "----------------------------------------" + eol +
				String.format("Total idle machine time: %.2f", state.getTotalIdleTime()) + eol +
				String.format("Total queueing time: %.2f",state.getTotalQueueTime()) + eol +
				String.format("Mean queueing time: %.2f", state.meanQueueingTime()) + eol +
				"Rejected cars: " + state.rejected + eol;
		return reportLine + stopMessage;

	}
	
}
