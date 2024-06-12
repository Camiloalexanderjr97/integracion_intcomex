package com.api.Integracion.serviceImpl;

import com.api.Integracion.Service.ProductService;
import com.api.Integracion.converter.ProductConverter;
import com.api.Integracion.entity.Product;
import com.api.Integracion.model.ProductModel;
import com.api.Integracion.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
	
	    @Autowired
	    private ProductRepository productRepository;

		@Autowired
		private ProductConverter productConverter;

	    public ProductModel createProduct(ProductModel product) {
			
				try {
				return	productConverter.entityToModel(productRepository.save(productConverter.modelToEntity(product)));
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
	    }

	    public Page<Product> getProducts(Pageable pageable) {
			try {
				return productRepository.findAll(pageable);

			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
	    }

	    public Optional<ProductModel> getProductById(int id) {
			try {
				return Optional.ofNullable(productConverter.entityToModel(productRepository.findById(id).get()));
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
	    }

		public boolean saveAll(List<Product> list){
			try {
				for(Product p: list ){
					createProduct(productConverter.entityToModel(p));
				}
				return true;
			}catch (Exception e){
				System.out.println(e);
				return false;
			}


		}
}
