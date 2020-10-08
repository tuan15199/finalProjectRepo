package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.dtos.ProductDetailDto;
import com.project.dtos.ProductReturnDto;
import com.project.model.ProductDetail;
import com.project.service.ProductDetailService;

@RestController
public class ProductDetailController {
	@Autowired
	private ProductDetailService service;
	
	@GetMapping(value = "productDetails")
	public List<ProductDetail> getAll() {
		return service.getAll();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "productDetail", consumes = "application/json")
	public ProductDetail createProDetail(@RequestBody ProductDetailDto productDetail) {
		return service.createProDetail(productDetail);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "updateProductDetail")
	public ProductDetail updateProDetail(@RequestBody ProductReturnDto productDetail) {
		return service.updateProductDetail(productDetail);
	}
}
