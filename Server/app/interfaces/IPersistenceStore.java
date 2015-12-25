package interfaces;

import java.time.Period;

import models.*;

public interface IPersistenceStore {
	/*
	 * Save Functions
	 */
	
	Boolean save(String data, String sensorName, SensorType sensortype);
	Boolean save(Range range, SensorType sensorType);
	Boolean saveWithReageException(String data,String sensorName,Range range, SensorType sensortype,double delta);
	
	/*
	 * Load Funcitons
	 */
	String loadData(String sensorName, Period timeInterval);
	String loadRange(SensorType sensorType, Period timeInterval);
	String loadLastRanges();
	String loadLastRange(SensorType sensorType);
	String loadCurrentSensorData(String sensorName);
	String loadCurrentSensorsData();
	String loadSensors();
}
