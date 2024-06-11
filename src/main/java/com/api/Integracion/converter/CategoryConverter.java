package com.api.Integracion.converter;

import com.api.Integracion.entity.Category;
import com.api.Integracion.model.CategoryModel;
import org.springframework.stereotype.Service;

@Service
public class CategoryConverter {

	
	public CategoryModel entityToModel(Category category) {
		CategoryModel model = new CategoryModel();
		model.setId(category.getId());
		model.setName(category.getName());
		model.setDescription(category.getDescription());
		model.setImageUrl(category.getImageUrl());
		return model;
	}
	public Category ModelToEntity(CategoryModel model) {
		Category category	= new Category();
		category.setId(model.getId());
		category.setName(model.getName());
		category.setDescription(model.getDescription());
		category.setImageUrl(model.getImageUrl());
		return category;
	}
}
