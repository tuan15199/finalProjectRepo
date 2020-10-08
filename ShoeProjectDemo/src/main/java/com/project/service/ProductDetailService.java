package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dtos.ProductDetailDto;
import com.project.dtos.ProductReturnDto;
import com.project.exception.DataNotFoundException;
import com.project.exception.InvalidQuantityException;
import com.project.model.Product;
import com.project.model.ProductDetail;
import com.project.repository.ProductDetailRepository;
import com.project.repository.ProductRepository;

@Service
@Transactional
public class ProductDetailService {
	@Autowired
	private ProductDetailRepository repo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private BrandService brandService;

	// validate size
	public void validateSize(Integer integer, String errorFeild) {
		if (integer == null || integer <= 0) {
			throw new InvalidQuantityException(errorFeild);
		}
	}
	
	// validate size
		public void validateNumber(Double number, String errorFeild) {
			if (number == null || number <= 0) {
				throw new InvalidQuantityException(errorFeild);
			}
		}

	public List<ProductDetail> getAll() {
		return repo.findAll();
	}

	public ProductDetail createProDetail(ProductDetailDto productDetail) {
		validateSize(productDetail.getSize(), "Size");
//		validateNumber(productDetail.getStar(), "Star");
		brandService.checkNull(productDetail.getColor(), "Color");
		
		Integer productId = productDetail.getProductId();
		Product product = null;

		if (productId != null) {
			product = productRepo.findById(productId)
					.orElseThrow(() -> new DataNotFoundException("product with id = " + productId));
		}

		ProductDetail newProductDetail = new ProductDetail(productDetail, product);
		newProductDetail = repo.save(newProductDetail);
		List<ProductDetail> proDetails = product.getProductDetails();
		proDetails.add(newProductDetail);
		product.setProductDetails(proDetails);
		return newProductDetail;
	}

	public ProductDetail updateProductDetail(ProductReturnDto productDetail) {
		validateSize(productDetail.getSize(), "Size");
		brandService.checkNull(productDetail.getColor(), "Color");
		
		ProductDetail existDetail = null;
		Integer detailID = productDetail.getDetailID();

		if (detailID != null) {
			existDetail = repo.findById(detailID)
					.orElseThrow(() -> new DataNotFoundException("ProductDetail with id = " + detailID));
		}

		existDetail.setColor(productDetail.getColor());
		existDetail.setGenderType(productDetail.getGenderType());
		existDetail.setSize(productDetail.getSize());

		return repo.save(existDetail);
	}
}
