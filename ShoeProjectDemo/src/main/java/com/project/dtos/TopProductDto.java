package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopProductDto {
	private String product;
	private String brand;
	private double totalSold;
}
