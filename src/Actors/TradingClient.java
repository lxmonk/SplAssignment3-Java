/**
 * 
 */
package Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author tom
 *
 */
public class TradingClient {
	Vector<StockOrder> _buyOrders;
	Vector<StockOrder> _sellOrders;
	boolean _open;
	

	public TradingClient() {
		_buyOrders = new Vector<StockOrder>();
		_sellOrders = new Vector<StockOrder>();
		_open = true;
	}
	
	public void addBuyOrder(StockOrder order) {
		_buyOrders.add(order);
	}

	public void addSellOrder(StockOrder order) {
		_sellOrders.add(order);
	}

	public void closeDay() {
		_open=false;
	}
	
	public Vector<StockOrder> getBuyOrders() {
		return _buyOrders;
	}
	
	public Vector<StockOrder> getSellOrders() {
		return _sellOrders;
	}
}
