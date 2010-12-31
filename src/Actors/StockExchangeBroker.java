/**
 * 
 */
package Actors;

/**
 * @author tom
 *
 */
public class StockExchangeBroker implements Comparable<StockExchangeBroker> {
	private final String _name;
	private int _numOfClients;
	private boolean _open;

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

	@Override
	public int compareTo(StockExchangeBroker o) {
		if (_numOfClients > o.getNumOfClients())
			return 1;
		else
			if (_numOfClients < o.getNumOfClients())
				return 1;
		return _name.compareTo(o.getName());
	}
}
