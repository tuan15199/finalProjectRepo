package com.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.project.dtos.ProductDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(length = 100)
	private double price;

	@ManyToOne
	private Catalog catalog;
	@ManyToOne
	private Brand brand;
	@OneToMany(mappedBy = "product")
	List<ProductDetail> productDetails = new ArrayList<ProductDetail>();

	public Product(ProductDto productDto , Catalog catalog, Brand brand) {
		this.setName(productDto.getName().toUpperCase());
		this.setPrice(productDto.getPrice());
	    this.setCatalog(catalog);
	    this.setBrand(brand);
	 }

}
