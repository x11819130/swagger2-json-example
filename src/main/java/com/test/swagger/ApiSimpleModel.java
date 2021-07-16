package com.test.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Swagger扩展注解
 * 用于application/json请求
 * 并使用诸如Map或JSONObject等非具体实体类接收参数时,对参数进行进一步描述
 * 简易描述 只有参数名
 *
 * @author chengz
 * @date 2020/10/14
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSimpleModel {
    /**
     * 分隔符
     *
     * @return separator
     */
    String separator() default ",";

    /**
     * 参数列表
     * 可以是字符串数组,也可以是一个字符串 多个字段以分隔符隔开: "id,name"
     *
     * @return value
     */
    String[] value() default {};
}