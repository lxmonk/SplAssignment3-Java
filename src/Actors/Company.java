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
	private int _dailyDelta;
	

	public Company(String name,int stocks,double price) {
		_buyOrders = new TreeMap<String, StockOrder>();
		_sellOrders = new TreeMap<String, StockOrder>();
		_name=name;
		_price=price;
		_numOfStocks=stocks;
		_floatingShares=stocks;
		_dailyDelta=0;
		addDefaultOrder();
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
		_dailyDelta+=order.getAmount();
		_buyOrders.put(order.getClientName(), order);
	}
	
	public void addBuyOrder(String client,String broker,int amount,String name,double price) {
		_dailyDelta+=amount;
		_buyOrders.put(client,new StockOrder("buyOrder",client,broker,amount,name,price));
	}

	public void addSellOrder(StockOrder order) {
		_dailyDelta-=order.getAmount();
		_sellOrders.put(order.getClientName(), order);
	}
	
	public void addSellOrder(String client,String broker,int amount,String name,double price) {
		_dailyDelta-=amount;
		_sellOrders.put(client,new StockOrder("sellOrder",client,broker,amount,name,price));
	}
	
	public TreeMap<String,StockOrder> getBuyOrders() {
		return _buyOrders;
	}
	
	public TreeMap<String,StockOrder> getSellOrders() {
		return _sellOrders;
	}
	
	private void computeNewPrice() {
		_price=_price*(1+(_dailyDelta/_numOfStocks));
	}
	
	public void endDay() {
		computeNewPrice();
		_dailyDelta=0;
		_buyOrders.clear();
		_sellOrders.clear();
		addDefaultOrder();
	}

	public void addDefaultOrder() {
		if (_floatingShares > 0)
			addSellOrder("StockExchange","StockExchange",_floatingShares,_name,_price);		
	}
}
