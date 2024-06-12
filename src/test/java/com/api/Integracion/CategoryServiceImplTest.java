package com.api.Integracion;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.Integracion.converter.CategoryConverter;
import com.api.Integracion.entity.Category;
import com.api.Integracion.model.CategoryModel;
import com.api.Integracion.repository.CategoryRepository;
import com.api.Integracion.serviceImpl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryConverter categoryConverter;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category categoryEntity;
    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        categoryEntity = new Category(1, "Category 1", "Description 1", "https://example.com/image1");
        categoryModel = new CategoryModel(1, "Category 1", "Description 1", "https://example.com/image1");
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryConverter.ModelToEntity(any(CategoryModel.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);

        Category result = categoryService.createCategory(categoryModel);

        verify(categoryConverter, times(1)).ModelToEntity(any(CategoryModel.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertNotNull(result);
        assertEquals("Category 1", result.getName());
    }

    @Test
    void testCreateCategory_Exception() {
        when(categoryConverter.ModelToEntity(any(CategoryModel.class))).thenReturn(categoryEntity);
        when(categoryRepository.save(any(Category.class))).thenThrow(new RuntimeException("Database error"));

        Category result = categoryService.createCategory(categoryModel);

        verify(categoryConverter, times(1)).ModelToEntity(any(CategoryModel.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertNull(result);
    }
}
