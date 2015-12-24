package model.convertors;

import interfaces.IConvertor;

public class HumidityConverter implements IConvertor<Double> {
	
	private final double LOW_BOUNDARY = 20;
	private final double HIGH_BOUNDARY = 80;

	@Override
	public Double convertValue(String value) {
		// TODO Auto-generated method stub
		return 0.0;
	}

}
