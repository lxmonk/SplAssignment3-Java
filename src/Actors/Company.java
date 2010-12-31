/**
 * 
 */
package Actors;

import java.util.Vector;

/**
 * @author tom
 *
 */
public class Company {
	final String _name;
	double _price;
	int _numOfShares;
	Vector<StockOrder> _buyOrders; // TODO: sort by price from small to big
	Vector<StockOrder> _sellOrders;
	

	public Company(String name,int stocks,double price) {
		_buyOrders = new Vector<StockOrder>();
		_sellOrders = new Vector<StockOrder>();
		_name=name;
		_price=price;
		_numOfShares=stocks;
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
	
	int getNumOfFlotingShares() {
		return _numOfShares;
	}
	
	void setNumOfFlotingShares(int shares) {
		_numOfShares=shares;
	}
	
	public void addBuyOrder(StockOrder order) {
		_buyOrders.add(order);
	}
	
	public void addBuyOrder(int amount,String client,double price) {
		_buyOrders.add(new StockOrder("buyOrder",amount,client,price));
	}

	public void addSellOrder(StockOrder order) {
		_sellOrders.add(order);
	}
	
	public void addSellOrder(int amount,String client,double price) {
		_buyOrders.add(new StockOrder("sellOrder",amount,client,price));
	}
	
	public Vector<StockOrder> getBuyOrders() {
		return _buyOrders;
	}
	
	public Vector<StockOrder> getSellOrders() {
		return _sellOrders;
	}
	
}
