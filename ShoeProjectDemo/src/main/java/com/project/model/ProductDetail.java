package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dtos.ProductDetailDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private GenderType genderType;
	@Column(nullable = false)
	private int star;
	private String color;
	private int size;
	private Status status;
	private String picture1;
	private String picture2;
	private String picture3;
	
	@JsonIgnore
	@ManyToOne
	private Product product;

	public ProductDetail(ProductDetailDto proDetailDto, Product product) {
		this.setColor(proDetailDto.getColor());
		this.setGenderType(proDetailDto.getGenderType());
		this.setSize(proDetailDto.getSize());
		this.setStar(proDetailDto.getStar());
		this.setStatus(proDetailDto.getStatus());
		this.setProduct(product);
		this.setPicture1(proDetailDto.getPicture1());
		this.setPicture2(proDetailDto.getPicture2());
		this.setPicture3(proDetailDto.getPicture3());
	}
	
}
