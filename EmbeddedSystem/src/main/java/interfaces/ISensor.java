package interfaces;

import models.*;

public interface ISensor {
	double getValue();
	String getName();
	SensorType getType();
}
