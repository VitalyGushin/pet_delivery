package app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Orders;

@Repository
public interface OrderRepository extends CrudRepository<Orders,Long> {
	
	Orders findByCustomer(String customer);

}
