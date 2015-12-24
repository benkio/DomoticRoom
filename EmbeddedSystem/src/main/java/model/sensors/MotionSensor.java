package model.sensors;

public class MotionSensor extends ASensor<Boolean> {

	private boolean value;
	
	@Override
	public Boolean getValue() {
		return value;
	}
}
