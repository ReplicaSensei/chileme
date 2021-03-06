package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.ProductCategory;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.CategoryForm;
import com.mamoru.chileme.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品类目
 */

@Controller
@RequestMapping("/seller/category")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list");
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                               Map<String, Object> map) {

        try {
            categoryService.delete(categoryId);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/category/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
        }

        return new ModelAndView("category/index");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() !=null) {
                categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);

            categoryService.save(productCategory);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/category/list");
        return new ModelAndView("common/success");
    }


}
