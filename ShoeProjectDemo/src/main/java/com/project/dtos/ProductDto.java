package com.project.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
	private Integer id;
	private String name;
	private String type;
	private Double price;
	private String brand;
	private Integer catalogId;
	private Integer brandId;
	
	public ProductDto(Integer id, String name, String type, Double price, String brand,
			Integer catalogId, Integer brandId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.catalogId = catalogId;
		this.brandId = brandId;
		this.brand = brand;
	}

}