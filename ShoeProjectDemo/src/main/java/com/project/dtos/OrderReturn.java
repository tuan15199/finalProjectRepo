package com.project.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderReturn {
	private Integer orderDetailId;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date orderDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date deliveryDate;
	private String product;
	private Integer quantity;
	private double totalPrice;
	
	public OrderReturn(Integer orderDetailId, String firstName, String lastName, Date orderDate, Date deliveryDate,
			String product, Integer quantity, double totalPrice) {
		this.orderDetailId = orderDetailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.product = product;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	
}
