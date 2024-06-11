package com.api.Integracion.Service;

import com.api.Integracion.entity.Product;
import com.api.Integracion.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

	  public abstract ProductModel createProduct(ProductModel product);

	    public abstract Page<Product> getProducts(Pageable pageable);

	    public abstract  Optional<ProductModel> getProductById(int id);

		public  abstract boolean saveAll(List< Product> list);
	    
}
