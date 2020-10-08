package com.project.dtos;

import com.project.model.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogDto {
	private Integer catalogId;
	private String type;
	private Integer brandId;

	public CatalogDto(String type, Integer brandId, Integer catalogId) {
		this.type = type;
		this.brandId = brandId;
		this.catalogId = catalogId;
	}

}
