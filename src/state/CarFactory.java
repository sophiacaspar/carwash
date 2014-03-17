package state;

/**
 * Creates the car.
 * 
 * @author emmacarlsson, sophiacaspar, malinross
 *
 */
public class CarFactory {
	private int id = 0;

	/**
	 * Returns the new car with an ID.
	 * @return new id
	 */
	public Car makeCar() {
		return new Car(id++);
	}
}
