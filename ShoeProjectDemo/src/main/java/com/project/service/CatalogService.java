package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dtos.ObjectReturn;
import com.project.exception.DataAlreadyExistException;
import com.project.model.Catalog;
import com.project.repository.CatalogRepository;

@Service
public class CatalogService {
	@Autowired
	private CatalogRepository repo;

	// check data already exist
	public void checkExistData(String name) {
		List<Catalog> catalogs = repo.findAll();
		for (Catalog catalog : catalogs) {
			if (name.equals(catalog.getType().toString())) {
				throw new DataAlreadyExistException(name);
			}
		}
	}

	public List<Catalog> getAll() {
		return repo.findAll();
	}

	public ObjectReturn<Catalog> getAllCatalog() {
		ObjectReturn<Catalog> list = new ObjectReturn<>();
		list.setData(repo.findAll());
		return list;
	}

	public Catalog createCatalog(Catalog catalog) {
		checkExistData(catalog.getType().toString());
		return repo.save(catalog);
	}
}
