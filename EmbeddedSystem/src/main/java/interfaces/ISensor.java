package interfaces;

import model.*;

public interface ISensor<T> {
	T getValue();
	String getName();
	SensorType getType();
}
