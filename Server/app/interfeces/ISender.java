package interfeces;

import model.*;

public interface ISender {
	void send(double data, String sensorName, SensorType sensorType);
}
