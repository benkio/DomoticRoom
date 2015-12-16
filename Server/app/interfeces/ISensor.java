package interfeces;

import model.*;

public interface ISensor {
	double getValue();
	String getName();
	SensorType getType();
}
