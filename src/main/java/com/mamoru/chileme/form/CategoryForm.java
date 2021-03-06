package com.mamoru.chileme.form;

import lombok.Data;

@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    private String categoryIcon;

    /** 类目编号. */
    private Integer categoryType;

}
