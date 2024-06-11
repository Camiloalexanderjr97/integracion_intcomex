package com.api.Integracion;

import com.api.Integracion.Service.CategoryService;
import com.api.Integracion.Service.ProductService;
import com.api.Integracion.controller.Controller;
import com.api.Integracion.entity.Category;
import com.api.Integracion.entity.Product;
import com.api.Integracion.model.CategoryModel;
import com.api.Integracion.model.ProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@SpringBootTest
class IntegracionApplicationTests {
	@Mock
	private CategoryService categoryService;

	@Mock
	private ProductService productService;

	@InjectMocks
	private Controller controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateCategory() {
		CategoryModel categoryModel = new CategoryModel(0, "SERVIDORES", "Description", "image_url");
		Category category = new Category();
		category.setId(1);
		category.setName("SERVIDORES");
		category.setDescription("Description");
		category.setImageUrl("image_url");

		when(categoryService.createCategory(any(CategoryModel.class))).thenReturn(category);

		ResponseEntity<Category> response = controller.createCategory(categoryModel);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(category, response.getBody());
	}

	@Test
	void testCreateProduct() {
		ProductModel productModel = new ProductModel();
		productModel.setName("Product");
		Product product = new Product();
		product.setId(1);
		product.setName("Product");

		when(productService.createProduct(any(ProductModel.class))).thenReturn(productModel);

		ResponseEntity<ProductModel> response = controller.createProduct(productModel);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}

	@Test
	void testGetProducts() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product());
		Page<Product> productPage = new PageImpl<>(productList);
		Pageable pageable = PageRequest.of(0, 10);

		when(productService.getProducts(pageable)).thenReturn(productPage);

		ResponseEntity<Page<Product>> response = controller.getProducts(0, 10);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(productPage, response.getBody());
	}

	@Test
	void testGetProductById() {
		ProductModel productModel = new ProductModel();
		productModel.setId(1);
		productModel.setName("Product");

		when(productService.getProductById(1)).thenReturn(Optional.of(productModel));

		ResponseEntity<ProductModel> response = controller.getProductById(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(productModel, response.getBody());
	}

	@Test
	void testGenerateProducts() {
		Category servers = new Category();
		servers.setId(1);
		servers.setName("SERVIDORES");
		servers.setDescription("Description");
		servers.setImageUrl("image_url");

		Category cloud = new Category();
		cloud.setId(2);
		cloud.setName("CLOUD");
		cloud.setDescription("Description");
		cloud.setImageUrl("image_url");

		when(categoryService.createCategory(any(CategoryModel.class))).thenReturn(servers, cloud);

		ResponseEntity<String> response = controller.generateProducts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("100000 products generated", response.getBody());
	}
}