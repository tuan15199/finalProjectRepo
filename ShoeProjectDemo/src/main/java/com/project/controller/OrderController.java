package com.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dtos.OrderDto;
import com.project.dtos.OrderReturn;
import com.project.dtos.TopCustomersDto;
import com.project.dtos.TopProductDto;
import com.project.model.Orders;
import com.project.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService service;
	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@GetMapping(value = "orders")
	public List<Orders> getAll() {
		logger.info("Show all orders!");
		return service.getAll();
	}

	@GetMapping(value = "order/{id}")
	public Orders getOrderById(@PathVariable Integer id) {
		logger.info("Show order id = " + id + "!");
		return service.getOrderById(id);
	}

	@GetMapping(value = "ordersByTime")
	public List<Orders> getOrderByTime(@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Show orders from " + fromDate + " to " + toDate + "!");
		return service.getOrderByRangeOfTime(fromDate, toDate);
	}

	@GetMapping(value = "ordersByDate")
	public List<Orders> getOrderByDate(@RequestParam String date) {
		logger.info("Show orders on " + date + "!");
		return service.getOrderByDate(date);
	}

	@GetMapping(value = "ordersByTime/deliveried")
	public List<Orders> getOrderSuccessByTime(@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Show all deliveried orders from " + fromDate + " to " + toDate + "!");
		return service.getOrderSuccessByRangeOfTime(fromDate, toDate);
	}

	@GetMapping(value = "ordersByTimeAndStatus")
	public List<Orders> getOrderByTimeAndStatus(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam int status) {
		logger.info("Show orders from " + fromDate + " to " + toDate + " having status = " + status + "!");
		return service.getOrderByTimeAndStatus(fromDate, toDate, status);
	}

	@GetMapping(value = "ordersByDate/deliveried")
	public List<Orders> getOrderSuccessByDate(@RequestParam String date) {
		logger.info("Show all deliveried orders on " + date + "!");
		return service.getOrderSuccessByDate(date);
	}

	@GetMapping(value = "ordersByDateAndStatus")
	public List<Orders> getOrderByDateAndStatus(@RequestParam String date, @RequestParam int status) {
		logger.info("Show all orders on " + date + " having status = " + status + "!");
		return service.getOrderByDateAndStatus(date, status);
	}

	@GetMapping(value = "ordersByStatus")
	public List<Orders> getOrderByStatus(@RequestParam int status) {
		logger.info("Show all orders having status = " + status + "!");
		return service.getOrderByStatus(status);
	}

	@GetMapping(value = "ordersSuccess")
	public List<Orders> getDeliveriedOrder() {
		logger.info("Show all deliveried orders!");
		return service.getDeliveriedOrder();
	}

	@GetMapping(value = "orderDetails/{id}")
	public List<OrderReturn> getOrderDetailByOrderId(@PathVariable Integer id) {
		logger.info("Show order detail having id = " + id + "!");
		return service.getOrderDetailByOrderId(id);
	}

	@PostMapping(value = "order")
	public Orders createOrder(@RequestBody OrderDto order) {
		logger.info("Create new order successfully!");
		return service.createOrder(order);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "orderVerified/{id}")
	public Orders updateVerified(@PathVariable Integer id) {
		logger.info("Order (id = " + id + ") is VERIFIED!");
		return service.orderVerified(id);
	}

	@PutMapping(value = "orderCancel/{id}")
	public Orders updateCancel(@PathVariable Integer id) {
		logger.warn("Order (id = " + id + ") is CANCELED!");
		return service.orderCancel(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "orderDeliveried/{id}")
	public Orders updateDeliveried(@PathVariable Integer id) {
		logger.info("Order (id = " + id + ") is DELIVERIED!");
		return service.orderDeliveried(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "topCustomers")
	public List<TopCustomersDto> getTopCustomers(@RequestParam int top, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Show top" + top + " customers from " + fromDate + " to " + toDate + "!");
		return service.getTopCustomers(top, fromDate, toDate);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "topProducts")
	public List<TopProductDto> getTopProducts(@RequestParam int top, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Show top" + top + " products from " + fromDate + " to " + toDate + "!");
		return service.getTopProduct(top, fromDate, toDate);
	}
}
