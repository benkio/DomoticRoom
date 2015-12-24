package model.sensors;

public class HumiditySensor extends ASensor<Double> {

	private double value;
	
	@Override
	public Double getValue() {
		return value;
	}
}
