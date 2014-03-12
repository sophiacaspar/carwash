package state;

public class CarFactory {
	private int id = 0;

	public Car makeCar() {
		return new Car(id++);
	}
}
