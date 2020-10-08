package com.project.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dtos.ProductReturnDto;
import com.project.dtos.ProductDto;
import com.project.dtos.CatalogDto;
import com.project.dtos.ObjectReturn;
import com.project.model.Product;
import com.project.model.Status;
import com.project.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService service;
	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@GetMapping(value = "/listProducts")
	public List<Product> listProduct() {
		logger.info("Show all products!");
		return service.getAll();
	}

	// get all product details
	@GetMapping(value = "listDetails")
	public ObjectReturn<ProductReturnDto> getAllDetail() {
		logger.info("Show all product details!");
		return service.getAllDetail();
	}

	// get all product detail of a product by ID
	@GetMapping(value = "/products/{id}")
	public ObjectReturn<ProductReturnDto> getAllProduct(@PathVariable int id) {
		logger.info("Show all product details of product having id = " + id + "!");
		return service.getDetailByProductId(id);
	}

	// get a specific product detail of a product by detail ID
	@GetMapping(value = "/products/detail/{id}")
	public ObjectReturn<ProductReturnDto> getAllProduct(@PathVariable int id, @RequestParam int detailID) {
		logger.info("Show product details having id = " + id + " where detailId = " + detailID + "!");
		return service.getDetailByID(id, detailID);
	}

	// get all product detail of a product by status
	@GetMapping(value = "/products/status/{id}")
	public ObjectReturn<ProductReturnDto> getAvailableProduct(@RequestParam Status status, @PathVariable int id) {
		logger.info("Show product details having id = " + id + " where status = " + status + "!");
		return service.getAvailableProduct(status, id);
	}

	// get all product detail of a product by Size
	@GetMapping(value = "/products/size/{id}")
	public ObjectReturn<ProductReturnDto> getProductBySize(@PathVariable int id, @RequestParam int size) {
		logger.info("Show product details having id = " + id + " where size = " + size + "!");
		return service.getProductBySize(id, size);
	}

	// get all product detail that still available by specific size
	@GetMapping(value = "/products/status/size/{id}")
	public ObjectReturn<ProductReturnDto> getAvailableProductBySize(@PathVariable int id, @RequestParam int size,
			@RequestParam Status status) {
		logger.info(
				"Show available product having id = " + id + " where size = " + size + " and status = " + status + "!");
		return service.getAvailableProductBySize(id, size, status);
	}

	// get all product that belong to a specific brand
	@GetMapping(value = "/products/brand/{id}")
	public ObjectReturn<ProductDto> getByBrand(@PathVariable int id) {
		logger.info("Show all products that belong to brand having id = " + id + "!");
		return service.getByBrand(id);
	}

	// get all product that belong to a specific type of a brand
	@GetMapping(value = "products/catalog")
	public ObjectReturn<ProductDto> getByCatalog(@RequestParam int brand, @RequestParam int catalog) {
		logger.info("Show all products that belong to brand = " + brand + ", catalog = " + catalog + "!");
		return service.getByBrandAndCatalog(brand, catalog);
	}

	// get info of all product with no detail include
	@GetMapping(value = "/simpleProducts")
	public ObjectReturn<ProductDto> getAll() {
		logger.info("Show all products without detail!");
		return service.findAll();
	}

	@GetMapping(value = "/product/{id}")
	public Product getById(@PathVariable int id) {
		logger.info("Show product having id = " + id + "!");
		return service.getById(id);
	}

	// get product details by product detail ID
	@GetMapping(value = "/productDetail/{id}")
	public ProductReturnDto getDetailByID(@PathVariable int id) {
		logger.info("Show product detail having id = " + id + "!");
		return service.getDetailByID(id);
	}

	// create product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/product", consumes = "application/json")
	public Product createProduct(@RequestBody ProductDto product) {
		logger.info("Create new product successfully!");
		return service.createProduct(product);
	}

	// update product
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/productUpdate/{id}", consumes = "application/json")
	public Product updateProduct(@RequestBody ProductDto product, @PathVariable Integer id) {
		logger.info("Update product (id = " + id + ") successfully!");
		return service.updateProduct(product);
	}

	// get all size of a product
	@GetMapping(value = "/sizes")
	public Set<Integer> getSize(@RequestParam int id) {
		logger.info("Show all product sizes where id = " + id + "!");
		return service.getSize(id);
	}

	// get all catalog of a brand
//	@GetMapping(value = "/catalogs/brand")
//	public ObjectReturn<CatalogDto> getCatalogByBrand() {
//		logger.info("Show all catalogs");
//		return service.getCatalogByBrand();
//	}

	// get product by Id
	@GetMapping(value = "/simpleProduct/{id}")
	public ProductDto getProductById(@PathVariable Integer id) {
		logger.info("Show product having id = " + id + "!");
		return service.getProductById(id);
	}
}
