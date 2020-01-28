package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.ProductCategory;
import com.mamoru.chileme.service.CategoryService;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/buyer/category")
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class BuyerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     * @param
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultVO list() {
        List<ProductCategory> categoryList = categoryService.findAll();
        return ResultVOUtil.success(categoryList);
    }
}
