package com.api.Integracion;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.Integracion.Service.CategoryService;
import com.api.Integracion.Service.ProductService;
import com.api.Integracion.controller.Controller;
import com.api.Integracion.entity.Category;
import com.api.Integracion.entity.Product;
import com.api.Integracion.model.CategoryModel;
import com.api.Integracion.model.ProductModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class IntegrationApplicationTests {

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private Controller controller;

    private CategoryModel categoryModel;
    private Category category;
    private ProductModel productModel;
    private Product product;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        categoryModel = new CategoryModel(0, "SERVIDORES", "Description", "image_url");
        category = new Category(1, "SERVIDORES", "Description", "image_url");

        productModel = new ProductModel();
        productModel.setName("Product 1");
        productModel.setDescription("Description 1");
        productModel.setPrice(BigDecimal.valueOf(100));

        product = new Product();
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(BigDecimal.valueOf(100));
        product.setCategory(category);
        productModel.setCategory(product);

        productList = new ArrayList<>();
        productList.add(product);
    }

    @Test
    void testCreateCategory() {
        when(categoryService.createCategory(any(CategoryModel.class))).thenReturn(category);

        ResponseEntity<Category> response = controller.createCategory(categoryModel);

        verify(categoryService, times(1)).createCategory(any(CategoryModel.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SERVIDORES", response.getBody().getName());
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(ProductModel.class))).thenReturn(productModel);

        ResponseEntity<ProductModel> response = controller.createProduct(productModel);

        verify(productService, times(1)).createProduct(any(ProductModel.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
    }

    @Test
    void testCreateProduct_InternalServerError() {
        when(productService.createProduct(any(ProductModel.class))).thenReturn(null);

        ResponseEntity<ProductModel> response = controller.createProduct(productModel);

        verify(productService, times(1)).createProduct(any(ProductModel.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productService.getProducts(pageable)).thenReturn(productPage);

        ResponseEntity<Page<Product>> response = controller.getProducts(0, 10);

        verify(productService, times(1)).getProducts(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void testGetProducts_InternalServerError() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productService.getProducts(pageable)).thenReturn(null);

        ResponseEntity<Page<Product>> response = controller.getProducts(0, 10);

        verify(productService, times(1)).getProducts(pageable);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(1)).thenReturn(Optional.of(productModel));

        ResponseEntity<ProductModel> response = controller.getProductById(1);

        verify(productService, times(1)).getProductById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        ResponseEntity<ProductModel> response = controller.getProductById(1);

        verify(productService, times(1)).getProductById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGenerateProducts() {
        Category servers = new Category(1, "SERVIDORES", "Description", "image_url");
        Category cloud = new Category(2, "CLOUD", "Description", "image_url");

        when(categoryService.createCategory(any(CategoryModel.class))).thenReturn(servers).thenReturn(cloud);
        when(productService.saveAll(anyList())).thenReturn(true);

        ResponseEntity<String> response = controller.generateProducts();

        verify(categoryService, times(2)).createCategory(any(CategoryModel.class));
        verify(productService, times(1)).saveAll(anyList());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("100000 products generated", response.getBody());
    }

   
}
