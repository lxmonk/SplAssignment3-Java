/**
 * 
 */
package Actors;

/**
 * @author tom
 *
 */
public class StockOrder implements Comparable<StockOrder>{	
	private final String _type;
	private final int _amount;
	private final String _name; //can be a stockName or a client name
	private final double _price;
	
	/**
	 * a StockOrder constructor 
	 * @param type type of order buy/sell
	 * @param amount amount of stocks
	 * @param name the name of the stock or the name of the client how made the order
	 * @param price price of the stock
	 */
	StockOrder(String type,int amount,String name,double price) {
		_type=type;
		_amount=amount;
		_name=name;
		_price=price;
	}
	
	String getType() {
		return _type;
	}
	
	int getAmount() {
		return _amount;
	}
	
	String getName() {
		return _name;
	}
	
	double getPrice() {
		return _price;
	}
	
	public String toString() {
		return _type + " " + _amount + " " + _name + " " + _price;
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
					return _name.compareTo(o.getName());
		} else
			if ((_type == "buyOrder") && (o.getType() == "buyOrder")) {
				if (_price < o.getPrice())
					return 1;
				else
					if (_price > o.getPrice())
						return -1;
					else
						return _name.compareTo(o.getName());
			} else
		return 0;
	}
}
