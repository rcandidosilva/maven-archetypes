package com.project.util.annotation;

public @interface BaseDataModel {

    public String method() default "listAllPaginated";

    public String methodCount() default "countAllPaginated";
}
