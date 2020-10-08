package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.model.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Integer>{

}
