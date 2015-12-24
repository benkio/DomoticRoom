package model.sensors;

public class LightSensor extends ASensor<Boolean> {

	private boolean value;
	
	@Override
	public Boolean getValue() {
		return value;
	}
}
