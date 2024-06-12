package com.api.Integracion.serviceImpl;

import com.api.Integracion.Service.CategoryService;
import com.api.Integracion.converter.CategoryConverter;
import com.api.Integracion.entity.Category;
import com.api.Integracion.model.CategoryModel;
import com.api.Integracion.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    public Category createCategory(CategoryModel model) {
        try {
            return categoryRepository.save(categoryConverter.ModelToEntity(model));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
            }
}