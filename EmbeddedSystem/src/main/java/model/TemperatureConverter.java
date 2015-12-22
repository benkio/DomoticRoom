package model;

import interfaces.IConvertor;

/*
 * This class convert the data in input in a valid value 
 * for the temperature. The sensor model is DHT11
 */
public class TemperatureConverter implements IConvertor {

	private final double LOW_BOUNDARY = 0;
	private final double HIGH_BOUNDARY = 50;
	
	@Override
	public double convertValue(String value) {
		// TODO Auto-generated method stub
		return 0;
	}

}
