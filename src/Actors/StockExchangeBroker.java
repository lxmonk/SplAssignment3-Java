/**
 * 
 */
package Actors;

/**
 * @author tom
 *
 */
public class StockExchangeBroker {
	final String _name;
	int _numOfClients;
	boolean _open;
	
	public StockExchangeBroker(String name) {
		_name=name;
		_numOfClients=0;
		_open=true;
	}
	
	void incClientNum() {
		_numOfClients++;
	}
	
	void decClientNum() {
		_numOfClients--;
	}
	
	String getName() {
		return _name;
	}
	
	int getNumOfClients() {
		return _numOfClients;
	}
	
	void closeDay(){
		_open=false;
	}
	
	boolean isWorking() {
		return _open;
	}
}
