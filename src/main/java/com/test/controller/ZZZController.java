package com.test.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 如果系统最后一个加载的接口加了@ApiGlobalModel/@ApiJsonModel/@ApiSimpleModel，这个接口动态生成的参数在swagger文档里将找不到
 * 出现这种情况就加上这个Controller，保证这个Controller是最后一个Controller => 保证最后一个接口没有使用以上3个注解
 */
@RestController
@RequestMapping("/whatever")
@Api(tags = "whatever")
public class ZZZController {
    @GetMapping("/whatever")
    public void whatever() {
    }
}
