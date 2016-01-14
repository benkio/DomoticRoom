package interfaces.dataFormatter;

//import rx.Observable;

public interface IDataFormatter<Object> {

	Object format(Object data);
	//Observable<Object> getFormattedStream(Observable<Object>);
}
