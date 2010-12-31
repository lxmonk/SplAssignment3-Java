/**
 * 
 */
package Actors;

import java.util.TreeMap;

/**
 * @author tom
 *
 */
public class Company {
	private final String _name;
	private double _price;
	private final int _numOfStocks;
	private int _floatingShares;
	private TreeMap<String,StockOrder> _buyOrders;
	private TreeMap<String,StockOrder> _sellOrders;
	

	public Company(String name,int stocks,double price) {
		_buyOrders = new TreeMap<String, StockOrder>();
		_sellOrders = new TreeMap<String, StockOrder>();
		_name=name;
		_price=price;
		_numOfStocks=stocks;
		_floatingShares=stocks;
	}
	
	String getName() {
		return _name;
	}
	
	double getPrice() {
		return _price;
	}
	
	void setPrice(double price) {
		_price=price;
	}
	
	int getOriginalNumOfStocks() {
		return _numOfStocks;
	}
	int getNumOfFlotingShares() {
		return _floatingShares;
	}
	
	void setNumOfFlotingShares(int shares) {
		_floatingShares=shares;
	}
	
	public void addBuyOrder(StockOrder order) {
		_buyOrders.put(order.getName(), order);
	}
	
	public void addBuyOrder(int amount,String client,double price) {
		_buyOrders.put(client,new StockOrder("buyOrder",amount,client,price));
	}

	public void addSellOrder(StockOrder order) {
		_sellOrders.put(order.getName(), order);
	}
	
	public void addSellOrder(int amount,String client,double price) {
		_sellOrders.put(client,new StockOrder("sellOrder",amount,client,price));
	}
	
	public TreeMap<String,StockOrder> getBuyOrders() {
		return _buyOrders;
	}
	
	public TreeMap<String,StockOrder> getSellOrders() {
		return _sellOrders;
	}
	
}
