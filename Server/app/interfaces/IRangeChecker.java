package interfaces;

import models.Range;
import models.SensorType;

public interface IRangeChecker {
	Boolean checkRange(String data, SensorType sensorType);
	void updateRange(Range range, SensorType sensorType);
}
