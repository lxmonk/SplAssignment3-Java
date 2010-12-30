/**
 * 
 */
package Actors;

/**
 * @author tom
 *
 */
public class StockOrder {	
	final String _type;
	final int _amount;
	final String _stockName;
	final Double _price;
	
	StockOrder(String type,int amount,String stockName,Double price) {
		_type=type;
		_amount=amount;
		_stockName=stockName;
		_price=price;
	}
	
	String getType() {
		return _type;
	}
	
	public String toString() {
		return _type + " " + _amount + " " + _stockName + " " + _price;
	}
}
