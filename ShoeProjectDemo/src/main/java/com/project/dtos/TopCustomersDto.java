package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopCustomersDto {
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private double totalPayment;
}
