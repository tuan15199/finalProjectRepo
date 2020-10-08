package com.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 100, nullable = false)
	private String firstName;
	@Column(length = 100, nullable = false)
	private String lastName;
	@Column(length = 12, nullable = false)
	private String phone;
	private String email;
	@Column(nullable = false)
	private String address;
	private double totalPrice;
	private OrderStatus status;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date orderDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date deliveryDate;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetail;

}
