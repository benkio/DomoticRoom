package model.sensors;

import interfaces.IConvertor;
import interfaces.ISensor;
import model.SensorType;

public abstract class ASensor<T> implements ISensor<T>{

	private String name;
	private IConvertor<T> convertor;
	private SensorType type;
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public SensorType getType(){
		return type;
	}
	
}
