package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.dtos.OrderReturn;
import com.project.dtos.TopCustomersDto;
import com.project.model.OrderStatus;
import com.project.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{

	@Query("SELECT new com.project.dtos.OrderReturn(od.id, o.firstName, o.lastName, o.orderDate, o.deliveryDate, p.name, od.quantity, od.totalPrice) FROM Orders o JOIN o.orderDetail od "
			+ "JOIN od.product pd JOIN pd.product p WHERE o.id = :id")
	List<OrderReturn> getOrderDetailByOrderId(Integer id);
	
	@Query(value = "SELECT * FROM orders WHERE order_date between :fromDate and :toDate", nativeQuery = true)
	List<Orders> getOrderByTime(String fromDate, String toDate);
	
	@Query(value = "SELECT * FROM orders WHERE order_date LIKE :date%", nativeQuery = true)
	List<Orders> getOrderByDate(String date);
	
	@Query(value = "SELECT * FROM orders WHERE order_date between :fromDate and :toDate and status = 3", nativeQuery = true)
	List<Orders> getOrderSuccessByTime(String fromDate, String toDate);
	
	@Query(value = "SELECT * FROM orders WHERE order_date between :fromDate and :toDate and status = :status", nativeQuery = true)
	List<Orders> getOrderByTimeAndStatus(String fromDate, String toDate, int status);
	
	@Query(value = "SELECT * FROM orders WHERE order_date LIKE :date% and status = 3", nativeQuery = true)
	List<Orders> getOrderSuccessByDate(String date);
	
	@Query(value = "SELECT * FROM orders WHERE order_date LIKE :date% and status = :status", nativeQuery = true)
	List<Orders> getOrderByDateAndStatus(String date, int status);
	
	@Query(value = "SELECT * FROM orders WHERE status = 3", nativeQuery = true)
	List<Orders> getDeliveriedOrder();
	
	@Query(value = "SELECT * FROM orders WHERE status = :status", nativeQuery = true)
	List<Orders> getOrderByStatus(int status);
	
	@Query(value = "SELECT phone, sum(total_price) FROM shoeappdemo.orders "
			+ "WHERE order_date between :fromDate and :toDate and status = 3"
			+ " GROUP BY phone ORDER BY sum(total_price) desc LIMIT :top", nativeQuery = true)
	List<String> getTopCustomer(int top, String fromDate, String toDate);
	
	@Query(value = "SELECT p.name as proName, b.name as brandName, sum(od.total_price) " + 
			"FROM order_detail od join product_detail pd on od.product_id = pd.id join orders o on o.id = od.order_id " + 
			"join product p on p.id = pd.product_id join brand b on b.id = p.brand_id " + 
			"WHERE o.order_date between :fromDate and :toDate and o.status = 3 " + 
			"GROUP BY p.name, b.name, od.product_id " + 
			"ORDER BY sum(od.total_price) desc " + 
			"LIMIT :top", nativeQuery = true)
	List<String> getTopProduct(int top, String fromDate, String toDate);
	
	List<Orders> findByPhone(String phone);
}
