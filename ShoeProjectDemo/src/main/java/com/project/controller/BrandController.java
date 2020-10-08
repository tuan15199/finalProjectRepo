package com.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.dtos.ObjectReturn;
import com.project.model.Brand;
import com.project.service.BrandService;

@RestController
public class BrandController {
	@Autowired
	private BrandService service;
	
	private Logger logger = LoggerFactory.getLogger(BrandController.class);

	@GetMapping(value = "/brands")
	public ObjectReturn<Brand> getAll() {
		logger.info("Show all brand!");
		return service.getAll();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/brand", consumes = "application/json")
	public Brand createBrand(@RequestBody Brand brand) {
		logger.info("Create new brand successfully!");
		return service.createBrand(brand);
	}
}
