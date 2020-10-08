package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer>{

}
