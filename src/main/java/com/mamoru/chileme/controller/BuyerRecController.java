package com.mamoru.chileme.controller;


import com.mamoru.chileme.entity.RecInfo;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.RecForm;
import com.mamoru.chileme.service.RecService;
import com.mamoru.chileme.utils.KeyUtil;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buyer/rec")
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class BuyerRecController {

    @Autowired
    private RecService recService;

    @GetMapping("/list")
    public ResultVO list (@RequestParam("buyerId") String buyerId) {
        List<RecInfo> recInfoList = recService.findList(buyerId);
        if (recInfoList==null) {
            return ResultVOUtil.error(500, "收货信息为空");
        }
        return ResultVOUtil.success(recInfoList);
    }

    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("recId") String recId) {
        RecInfo recInfo = recService.findOne(recId);
        if (recInfo==null) {
            return ResultVOUtil.error(500, "参数错误");
        }
        return ResultVOUtil.success(recInfo);
    }


    @PostMapping("/save")
    public ResultVO save (@Valid RecForm form,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(500, "参数不正确");
        }
        RecInfo recInfo = new RecInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getRecId())) {
                recInfo = recService.findOne(form.getRecId());
            } else {
                form.setRecId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, recInfo);
            recService.save(recInfo);
        } catch (ChilemeException e) {
            return ResultVOUtil.error(500, "保存失败");
        }
        return ResultVOUtil.success(recInfo);
    }

    @PostMapping("/del")
    public ResultVO delete (@Valid RecForm form,
                            BindingResult bindingResult) {
        RecInfo recInfo = recService.findOne(form.getRecId());
        if (recInfo==null) {
            return ResultVOUtil.error(500, "收货信息不存在");
        }
        recService.delete(recInfo);
        return ResultVOUtil.success();
    }

}
