/**
 * 
 */
package brokerPac;

/**
 * @author tom
 *
 */
public class StockOrder implements Comparable<StockOrder>{	
	private final String _type;
	private final int _amount;
	private final String _stockname; 
	private final String _clientName;
	private final String _brokerName;
	private final double _price;
	
	/**
	 * a StockOrder constructor 
	 * @param type type of order buy/sell
	 * @param amount amount of stocks
	 * @param name the name of the stock or the name of the client how made the order
	 * @param price price of the stock
	 */
	public StockOrder(String type,String clientName,String brokerName,int amount,String stockname,double price) {
		_type=type;
		_amount=amount;
		_stockname=stockname;
		_clientName=clientName;
		_brokerName=brokerName;
		_price=price;
	}
	
	public String getType() {
		return _type;
	}
	
	public int getAmount() {
		return _amount;
	}
	
	public String getStockName() {
		return _stockname;
	}
	
	public String getClientName() {
		return _clientName;
	}
	
	public String getBrokerName() {
		return _brokerName;
	}
	
	public double getPrice() {
		return _price;
	}
	
	public String toString() {
		return _type+" "+ _clientName + " "+_brokerName +" "+ _amount + " " + _stockname + " " + _price;
	}

	@Override
	public int compareTo(StockOrder o) {
		if ((_type == "sellOrder") && (o.getType() == "sellOrder")) {
			if (_price > o.getPrice())
				return 1;
			else
				if (_price < o.getPrice())
					return -1;
				else
					return _clientName.compareTo(o.getClientName());
		} else
			if ((_type == "buyOrder") && (o.getType() == "buyOrder")) {
				if (_price < o.getPrice())
					return 1;
				else
					if (_price > o.getPrice())
						return -1;
					else
						return _clientName.compareTo(o.getClientName());
			} else
		return 0;
	}
}
