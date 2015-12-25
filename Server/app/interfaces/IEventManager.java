package interfaces;

import models.*;

public interface IEventManager {
	void newData(String data, String sensorName, SensorType sensorType);
	void newRange(Range range, SensorType sensorType);
}
