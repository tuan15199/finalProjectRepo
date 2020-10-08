package com.project.dtos;

import com.project.model.GenderType;
import com.project.model.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDto {
	private int id;
	private GenderType genderType;
	private String color;
	private int size;
	private int star;
	private Status status;
	private int productId;
	private String picture1;
	private String picture2;
	private String picture3;
	
}
