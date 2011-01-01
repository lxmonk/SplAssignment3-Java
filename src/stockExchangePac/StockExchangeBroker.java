/**
 * 
 */
package stockExchangePac;

/**
 * @author tom
 * @info a object representing a broker in the stockExchnage 
 */

public class StockExchangeBroker implements Comparable<StockExchangeBroker> {
	private final String _name;
	private int _numOfClients;

	/**
	 * a new {@link StockExchangeBroker}
	 * @param name 
	 */
	public StockExchangeBroker(String name) {
		_name=name;
		_numOfClients=0;
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
