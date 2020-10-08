package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dtos.ObjectReturn;
import com.project.exception.DataAlreadyExistException;
import com.project.exception.InvalidNameException;
import com.project.model.Brand;
import com.project.repository.BrandRepository;

@Service
public class BrandService {
	@Autowired
	private BrandRepository repo;

	// check weather name is null
	public void checkNull(String name, String errorFeild) {
		if (name == null || name.equals("")) {
			throw new InvalidNameException(errorFeild);
		}
	}

	// check data already exist
	public void checkExistData(String name) {
		List<Brand> brands = repo.findAll();
		for (Brand brand : brands) {
			if (name.equals(brand.getName())) {
				throw new DataAlreadyExistException(name);
			}
		}
	}

	public ObjectReturn<Brand> getAll() {
		ObjectReturn<Brand> list = new ObjectReturn<>();
		list.setData(repo.findAll());
		return list;
	}

	public Brand createBrand(Brand input) {
		checkNull(input.getName(), "Brand name");
		checkExistData(input.getName());
		return repo.save(input);
	}
}
