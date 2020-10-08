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
import com.project.model.Catalog;
import com.project.service.CatalogService;

@RestController
public class CatalogController {
	@Autowired
	private CatalogService service;
	private Logger logger = LoggerFactory.getLogger(CatalogController.class);
	
	@GetMapping(value = "catalogs")
	public ObjectReturn<Catalog> getAllCatalogs() {
		logger.info("Show all catalogs!");
		return service.getAllCatalog();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "catalog", consumes = "application/json")
	public Catalog createCatalog(@RequestBody Catalog catalog) {
		logger.info("Create new catalog successfully!");
		return service.createCatalog(catalog);
	}
}