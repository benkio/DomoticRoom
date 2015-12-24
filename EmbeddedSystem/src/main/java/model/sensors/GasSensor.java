package model.sensors;

public class GasSensor extends ASensor<Boolean> {

	private boolean value;
	
	@Override
	public Boolean getValue() {
		return value;
	}
}
