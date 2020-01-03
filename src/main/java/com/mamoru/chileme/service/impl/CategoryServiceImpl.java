package com.mamoru.chileme.service.impl;

import com.mamoru.chileme.dao.ProductCategoryDao;
import com.mamoru.chileme.entity.ProductCategory;
import com.mamoru.chileme.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao dao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return dao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return dao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return dao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return dao.save(productCategory);
    }
}
