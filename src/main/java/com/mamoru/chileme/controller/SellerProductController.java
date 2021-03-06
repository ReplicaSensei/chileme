package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.ProductCategory;
import com.mamoru.chileme.entity.ProductInfo;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.ProductForm;
import com.mamoru.chileme.service.CategoryService;
import com.mamoru.chileme.service.ProductService;
import com.mamoru.chileme.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "8") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page-1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 下架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (ChilemeException e){
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam(value = "productId", required = false) String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam(value = "productId", required = false) String productId,
                               Map<String, Object> map) {

        try {
            productService.delete(productId);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 展示
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);


        return new ModelAndView("product/index", map);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/chileme/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/product/list");
        return new ModelAndView("common/success", map);
    }

}
