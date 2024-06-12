package com.api.Integracion.controller;

import com.api.Integracion.Service.CategoryService;
import com.api.Integracion.Service.ProductService;
import com.api.Integracion.entity.Category;
import com.api.Integracion.entity.Product;
import com.api.Integracion.model.CategoryModel;
import com.api.Integracion.model.ProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/integracion")
public class Controller {

	    @Autowired
	    private CategoryService categoryService;

	    @PostMapping("/category")
	    public ResponseEntity<Category> createCategory(@RequestBody CategoryModel category) {
	        return ResponseEntity.ok(categoryService.createCategory(category));
	    }
	
   @Autowired
	    private ProductService productService;

		@PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/product")
	    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) {
				ProductModel pModel = productService.createProduct(product);
				if(pModel!=null)
				return ResponseEntity.ok(pModel);
				else{
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			
	    }

	    @GetMapping("/product")
	    public ResponseEntity<Page<Product>> getProducts(@RequestParam int page, @RequestParam int size) {
	        Pageable pageable = PageRequest.of(page, size);
			Page<Product> rta= productService.getProducts(pageable);
			if(rta!=null)
				return ResponseEntity.ok(rta);
			else{
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}

	    }

	    @GetMapping("/product/{id}")
	    public ResponseEntity<ProductModel> getProductById(@PathVariable int id) {
	        return productService.getProductById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/generate")
	public ResponseEntity<String> generateProducts() {
	    List<Product> products = new ArrayList<>();
	    Category servers = categoryService.createCategory(new CategoryModel(0, "SERVIDORES", "Description", "image_url"));
	    Category cloud = categoryService.createCategory(new CategoryModel(0, "CLOUD", "Description", "image_url"));
		if((servers != null) || (cloud != null )){
	    for (int i = 0; i < 100000; i++) {
	        Product product = new Product();
	        product.setName("Product " + i);
	        product.setDescription("Description " + i);
	        product.setPrice(BigDecimal.valueOf(Math.random() * 1000));
	        product.setCategory(i % 2 == 0 ? servers : cloud);
	        product.setImageUrl("https://example.com/image" + i);
	        products.add(product);
	    }
			boolean rta=  productService.saveAll(products);
			if(rta)
				return ResponseEntity.ok("100000 products generated");
				else{
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		else{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
