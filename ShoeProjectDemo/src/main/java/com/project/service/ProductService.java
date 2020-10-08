package com.project.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dtos.ProductReturnDto;
import com.project.exception.DataNotFoundException;
import com.project.dtos.ProductDto;
import com.project.dtos.CatalogDto;
import com.project.dtos.ObjectReturn;
import com.project.model.Catalog;
import com.project.model.Product;
import com.project.model.Status;
import com.project.repository.CatalogRepository;
import com.project.repository.ProductRepository;
import com.project.model.Brand;
import com.project.repository.BrandRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductRepository repo;
	@Autowired
	private CatalogRepository catalogRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ProductDetailService detailService;
	@Autowired
	private BrandService brandService;

	public List<Product> getAll() {
		return repo.findAll();
	}

	// get all product details
	public ObjectReturn<ProductReturnDto> getAllDetail() {
		ObjectReturn<ProductReturnDto> listDetails = new ObjectReturn<>();
		listDetails.setData(repo.getAllDetail());
		return listDetails;
	}

	// get info of all product with no detail include
	public ObjectReturn<ProductDto> findAll() {
		ObjectReturn<ProductDto> list = new ObjectReturn<>();
		list.setData(repo.getAll());
		return list;
	}

	// get all product that belong to a specific brand
	public ObjectReturn<ProductDto> getByBrand(int id) {
		ObjectReturn<ProductDto> list = new ObjectReturn<>();
		list.setData(repo.getByBrand(id));
		return list;
	}

	// get all product that belong to a specific type of a brand and catalog
	public ObjectReturn<ProductDto> getByBrandAndCatalog(int brandID, int cataID) {
		ObjectReturn<ProductDto> list = new ObjectReturn<>();
		list.setData(repo.getByBrandAndCatalog(brandID, cataID));
		return list;
	}

	// get all product detail of a product by ID
	public ObjectReturn<ProductReturnDto> getDetailByProductId(int id) {
		ObjectReturn<ProductReturnDto> list = new ObjectReturn<>();
		list.setData(repo.getDetailByProductId(id));
		return list;
	}

	// get a specific product detail of a product by detail ID
	public ObjectReturn<ProductReturnDto> getDetailByID(int id, int dtid) {
		ObjectReturn<ProductReturnDto> list = new ObjectReturn<>();
		list.setData(repo.getDetailById(id, dtid));
		return list;
	}

	// get all product detail of a product by status
	public ObjectReturn<ProductReturnDto> getAvailableProduct(Status status, int id) {
		ObjectReturn<ProductReturnDto> list = new ObjectReturn<>();
		list.setData(repo.getAvailableProductById(status, id));
		return list;
	}

	// get all product detail of a product by size
	public ObjectReturn<ProductReturnDto> getProductBySize(int id, int size) {
		ObjectReturn<ProductReturnDto> list = new ObjectReturn<>();
		list.setData(repo.getProductBySize(id, size));
		return list;
	}

	// get all product detail that still available by specific size
	public ObjectReturn<ProductReturnDto> getAvailableProductBySize(int id, int size, Status status) {
		ObjectReturn<ProductReturnDto> list = new ObjectReturn<>();
		list.setData(repo.getAvailableProductBySize(id, size, status));
		return list;
	}

	public Product getById(int id) {
		return repo.findById(id).get();
	}

	// get all size of a product
	public Set<Integer> getSize(int id) {
		return repo.getSize(id);
	}

	// get all catalog of a brand
//	public ObjectReturn<CatalogDto> getCatalogByBrand() {
//		ObjectReturn<CatalogDto> list = new ObjectReturn<>();
//		list.setData(repo.getCatalogByBrand());
//		return list;
//	}

	// get product by id
	public ProductDto getProductById(int id) {
		return repo.getByProID(id);
	}

	// get product details by product detail ID
	public ProductReturnDto getDetailByID(int id) {
		return repo.getDetailByID(id);
	}
	
	// get product details by product detail color
		public ProductReturnDto getDetailByColor(String color) {
			return repo.getDetailByColor(color);
		}

	// create product
	public Product createProduct(ProductDto productDto) {
		detailService.validateNumber(productDto.getPrice(), "Price");
		brandService.checkNull(productDto.getName(), "Name");

		Integer catalogId = productDto.getCatalogId();
		Catalog catalog = null;
		Integer brandId = productDto.getBrandId();
		Brand brand = null;

		if (catalogId != null) {
			catalog = catalogRepo.findById(catalogId)
					.orElseThrow(() -> new DataNotFoundException("Catalog with id = " + catalogId));
		}

		if (brandId != null) {
			brand = brandRepo.findById(brandId)
					.orElseThrow(() -> new DataNotFoundException("Brand with id = " + brandId));
		}

		Product newProduct = new Product(productDto, catalog, brand);
		newProduct = repo.save(newProduct);
		return newProduct;
	}

	// edit product
	public Product updateProduct(ProductDto productDto) {
		if(productDto.getPrice() != null)
			detailService.validateNumber(productDto.getPrice(), "Price");
		if(productDto.getName() != null)
			brandService.checkNull(productDto.getName(), "Name");

		Integer productId = productDto.getId();
		Integer catalogId = productDto.getCatalogId();
		Catalog catalog = null;
		Integer brandId = productDto.getBrandId();
		Brand brand = null;
		
		Product existProduct = getById(productId);

		if (catalogId != null) {
			catalog = catalogRepo.findById(catalogId)
					.orElseThrow(() -> new DataNotFoundException("Catalog with id = " + catalogId));
			existProduct.setCatalog(catalog);
		}

		if (brandId != null) {
			brand = brandRepo.findById(brandId).orElseThrow(() -> new DataNotFoundException("Brand with id = " + brandId));
			existProduct.setBrand(brand);
		}

		existProduct.setName(productDto.getName());
		existProduct.setPrice(productDto.getPrice());

		return repo.save(existProduct);
	}

}
