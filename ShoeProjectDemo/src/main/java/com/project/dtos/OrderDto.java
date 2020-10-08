package com.project.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	List<OrderDetailDto> listDetail;
}
