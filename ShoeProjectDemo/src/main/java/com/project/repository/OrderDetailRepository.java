package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
