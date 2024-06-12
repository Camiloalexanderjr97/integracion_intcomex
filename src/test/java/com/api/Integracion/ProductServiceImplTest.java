package com.api.Integracion;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

import com.api.Integracion.converter.ProductConverter;
import com.api.Integracion.entity.Product;
import com.api.Integracion.model.ProductModel;
import com.api.Integracion.repository.ProductRepository;
import com.api.Integracion.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product productEntity;
    private ProductModel productModel;

    @BeforeEach
    void setUp() {
        productEntity = new Product(1, "Product 1", "Description 1", BigDecimal.valueOf(100), null, "https://example.com/image1");
        productModel = new ProductModel(1, "Product 1", "Description 1", BigDecimal.valueOf(100), null, "https://example.com/image1");
    }

    @Test
    void testCreateProduct_Success() {
        when(productConverter.modelToEntity(any(ProductModel.class))).thenReturn(productEntity);
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);
        when(productConverter.entityToModel(any(Product.class))).thenReturn(productModel);

        ProductModel result = productService.createProduct(productModel);

        verify(productConverter, times(1)).modelToEntity(any(ProductModel.class));
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productConverter, times(1)).entityToModel(any(Product.class));
        assertNotNull(result);
        assertEquals("Product 1", result.getName());
    }

    @Test
    void testCreateProduct_Exception() {
        when(productConverter.modelToEntity(any(ProductModel.class))).thenReturn(productEntity);
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        ProductModel result = productService.createProduct(productModel);

        verify(productConverter, times(1)).modelToEntity(any(ProductModel.class));
        verify(productRepository, times(1)).save(any(Product.class));
        assertNull(result);
    }

    @Test
    void testGetProducts_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(productEntity));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Product> result = productService.getProducts(pageable);

        verify(productRepository, times(1)).findAll(any(Pageable.class));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetProducts_Exception() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Database error"));

        Page<Product> result = productService.getProducts(pageable);

        verify(productRepository, times(1)).findAll(any(Pageable.class));
        assertNull(result);
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productEntity));
        when(productConverter.entityToModel(any(Product.class))).thenReturn(productModel);

        Optional<ProductModel> result = productService.getProductById(1);

        verify(productRepository, times(1)).findById(anyInt());
        verify(productConverter, times(1)).entityToModel(any(Product.class));
        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getName());
    }

    @Test
    void testGetProductById_Exception() {
        when(productRepository.findById(anyInt())).thenThrow(new RuntimeException("Database error"));

        Optional<ProductModel> result = productService.getProductById(1);

        verify(productRepository, times(1)).findById(anyInt());
        assertNull(result);
    }

    @Test
    void testSaveAll_Success() {
        List<Product> productList = List.of(productEntity);
        when(productConverter.entityToModel(any(Product.class))).thenReturn(productModel);
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);

        boolean result = productService.saveAll(productList);

        verify(productConverter, times(productList.size())).entityToModel(any(Product.class));
        assertTrue(result);
    }

    @Test
    void testSaveAll_Exception() {
        List<Product> productList = List.of(productEntity);
        when(productConverter.entityToModel(any(Product.class))).thenThrow(new RuntimeException("Conversion error"));

        boolean result = productService.saveAll(productList);

        verify(productConverter, times(1)).entityToModel(any(Product.class));
        verify(productRepository, never()).save(any(Product.class));
        assertFalse(result);
    }
}
