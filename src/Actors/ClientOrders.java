/**
 * 
 */
package Actors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tom
 *
 */
public class ClientOrders {
	List<StockOrder> _buyOrders;
	List<StockOrder> _sellOrders;

	public ClientOrders() {
		_buyOrders = new ArrayList<StockOrder>();
		_sellOrders = new ArrayList<StockOrder>();
	}
	
	void addOrder(StockOrder order) {
		if (order.getType() == "buyOrder")
			_buyOrders.add(order);
		else
			if (order.getType() == "sellOrder")
				_sellOrders.add(order);
			// else TODO:throw exeption; 
	}
}
