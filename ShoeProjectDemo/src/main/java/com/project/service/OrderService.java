package com.project.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dtos.OrderDto;
import com.project.dtos.OrderReturn;
import com.project.dtos.TopCustomersDto;
import com.project.dtos.TopProductDto;
import com.project.exception.DataNotFoundException;
import com.project.model.OrderDetail;
import com.project.model.OrderStatus;
import com.project.model.Orders;
import com.project.model.ProductDetail;
import com.project.repository.OrderDetailRepository;
import com.project.repository.OrdersRepository;
import com.project.repository.ProductDetailRepository;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrdersRepository orderRepo;
	@Autowired
	private OrderDetailRepository ordDetailRepo;
	@Autowired
	private ProductDetailRepository prodetailRepo;
	@Autowired
	private MailService mailService;
	@Autowired
	private BrandService brandService;

	public List<Orders> getAll() {
		return orderRepo.findAll();
	}

	public List<Orders> getOrderByRangeOfTime(String fromDate, String toDate) {
		return orderRepo.getOrderByTime(fromDate, toDate);
	}

	public List<Orders> getOrderByDate(String date) {
		return orderRepo.getOrderByDate(date);
	}

	public List<Orders> getOrderSuccessByRangeOfTime(String fromDate, String toDate) {
		return orderRepo.getOrderSuccessByTime(fromDate, toDate);
	}

	public List<Orders> getOrderByTimeAndStatus(String fromDate, String toDate, int status) {
		return orderRepo.getOrderByTimeAndStatus(fromDate, toDate, status);
	}

	public List<Orders> getOrderSuccessByDate(String date) {
		return orderRepo.getOrderSuccessByDate(date);
	}

	public List<Orders> getOrderByDateAndStatus(String date, int status) {
		return orderRepo.getOrderByDateAndStatus(date, status);
	}

	public List<Orders> getDeliveriedOrder() {
		return orderRepo.getDeliveriedOrder();
	}

	public Orders getOrderById(Integer id) {
		return orderRepo.findById(id).orElseThrow(() -> new DataNotFoundException("order with id =" + id));
	}

	public List<Orders> getOrderByStatus(int status) {
		return orderRepo.getOrderByStatus(status);
	}

	public List<OrderReturn> getOrderDetailByOrderId(Integer id) {
		return orderRepo.getOrderDetailByOrderId(id);
	}

	public Orders createOrder(OrderDto orderDto) {
		brandService.checkNull(orderDto.getFirstName(), "First name");
		brandService.checkNull(orderDto.getLastName(), "Last name");
		brandService.checkNull(orderDto.getPhoneNumber(), "phone number");

		Orders order = new Orders();
		ArrayList<OrderDetail> orderDetails = new ArrayList<>();

		order.setFirstName(orderDto.getFirstName());
		order.setLastName(orderDto.getLastName());
		order.setPhone(orderDto.getPhoneNumber());
		order.setAddress(orderDto.getAddress());
		order.setEmail(orderDto.getEmail());

		order.setStatus(OrderStatus.PROCESSING);
		Date today = new Date();
		order.setOrderDate(today);
		order.setDeliveryDate(null);

		double totalPrice = 0;
		for (int i = 0; i < orderDto.getListDetail().size(); i++) {

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setQuantity(orderDto.getListDetail().get(i).getQuantity());

			Integer productId = orderDto.getListDetail().get(i).getProductDetailID();
			ProductDetail product = prodetailRepo.findById(productId)
					.orElseThrow(() -> new DataNotFoundException("Product detail with id = " + productId));

			orderDetail.setProduct(product);

			orderDetail.setPerPrice(product.getProduct().getPrice());
			orderDetail.setTotalPrice(orderDetail.getPerPrice() * orderDto.getListDetail().get(i).getQuantity());
			totalPrice += orderDetail.getTotalPrice();
			orderDetails.add(ordDetailRepo.save(orderDetail));

		}

		order.setTotalPrice(totalPrice);
		order.setOrderDetail(orderDetails);
		order = orderRepo.save(order);

		for (OrderDetail orderDetail : orderDetails) {
			orderDetail.setOrder(order);
			ordDetailRepo.save(orderDetail);
		}

		if (order.getEmail() != null) {
			if (!order.getEmail().equals(""))
				try {
					mailService.sendEmail(orderDto);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
				}
		}
		return order;
	}

	public Orders orderVerified(int id) {
		Orders order = getOrderById(id);
		order.setStatus(OrderStatus.VERIFIED);
		return orderRepo.save(order);
	}

	public Orders orderCancel(int id) {
		Orders order = getOrderById(id);
		order.setStatus(OrderStatus.CANCEL);
		return orderRepo.save(order);
	}

	public Orders orderDeliveried(int id) {
		Orders order = getOrderById(id);
		order.setStatus(OrderStatus.DELIVERIED);
		Date today = new Date();
		order.setDeliveryDate(today);
		return orderRepo.save(order);
	}

	public List<TopCustomersDto> getTopCustomers(int top, String fromDate, String toDate) {
		List<String> resultList = new ArrayList<String>();
		List<TopCustomersDto> customerList = new ArrayList<>();
		resultList = orderRepo.getTopCustomer(top, fromDate, toDate);
		for (String phone : resultList) {
			List<Orders> orders = orderRepo.findByPhone(phone.split(",")[0]);
			TopCustomersDto customer = new TopCustomersDto();
			customer.setFirstName(orders.get(0).getFirstName());
			customer.setLastName(orders.get(0).getLastName());
			customer.setEmail(orders.get(0).getEmail());
			customer.setPhone(phone.split(",")[0]);
			customer.setTotalPayment(Double.parseDouble((phone.split(",")[1])));
			customerList.add(customer);
		}
		return customerList;
	}
	
	public List<TopProductDto> getTopProduct(int top, String fromDate, String toDate) {
		List<String> resultList = new ArrayList<String>();
		resultList = orderRepo.getTopProduct(top, fromDate, toDate);
		List<TopProductDto> productList = new ArrayList<>();
		
		for (String pro : resultList) {
			TopProductDto product = new TopProductDto();
			product.setProduct(pro.split(",")[0]);
			product.setBrand(pro.split(",")[1]);
			product.setTotalSold(Double.parseDouble(pro.split(",")[2]));
			productList.add(product);
		}
		return productList;
	}

}
