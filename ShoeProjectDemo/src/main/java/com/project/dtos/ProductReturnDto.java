package com.project.dtos;

import com.project.model.GenderType;
import com.project.model.Status;
import com.project.model.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReturnDto {
	private int id;
	private int detailID;
	private String brand;
	private String name;
	private double price;
	private String color;
	private int size;
	private GenderType genderType;
	private int star;
	private Status status;
	private String picture1;
	private String picture2;
	private String picture3;

	private int catalogID;
	private String type;
	public ProductReturnDto(int id, int detailID, String brand, String name, double price, String color, int size, GenderType genderType, 
			int star, Status status, String picture1, String picture2, String picture3, int catalogID, String type) {
		this.id = id;
		this.detailID = detailID;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.color = color;
		this.size = size;
		this.genderType = genderType;
		this.star = star;
		this.status = status;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.catalogID = catalogID;
		this.type = type;
	}
}
