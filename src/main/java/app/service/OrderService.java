package app.service;

import java.util.Date;
import java.util.List;

public interface OrderService {
	
	void addOrder(Date deliver, String customer, String address, String product, String phone);
	
	void changeOrder(Long id, Date newDeliver);
	
	void delivereOrder(Long id);
	
	void postponeOrder(Long id);
	
	List<String> undeliveredOrders();
	
	List<String> postponedOrders();
	
}
