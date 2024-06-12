package com.api.Integracion.converter;

import com.api.Integracion.entity.Product;
import com.api.Integracion.model.ProductModel;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {

	
	public Product modelToEntity(ProductModel model) {
		Product product = new Product();
		product.setId(model.getId());
		product.setName(model.getName());
		product.setDescription(model.getDescription());
		product.setPrice(model.getPrice());
		return product;
	}

	public ProductModel entityToModel(Product product){
		ProductModel model = new ProductModel();
		model.setId(product.getId());
		model.setDescription(product.getDescription());
		model.setName(product.getName());
		model.setPrice(product.getPrice());
		return model;
	}
}
