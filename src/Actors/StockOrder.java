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
	final int _price;
	
	StockOrder(String type,int amount,String stockName,int price) {
		_type=type;
		_amount=amount;
		_stockName=stockName;
		_price=price;
	}
	
	String getType() {
		return _type;
	}
}
