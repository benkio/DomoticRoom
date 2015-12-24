package model.sensors;

public class TemperatureSensor extends ASensor<Double> {

	private double value;
	
	@Override
	public Double getValue() {
		return value;
	}
}
