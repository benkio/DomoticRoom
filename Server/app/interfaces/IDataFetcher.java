package interfaces;

public interface IDataFetcher {
	void addSensor(ISensor sensor);
	void fetchDataAndNotify();
}
