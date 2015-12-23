package interfaces;

import models.*;

public interface ISender {
	void send(double data, String sensorName, SensorType sensorType);
}
