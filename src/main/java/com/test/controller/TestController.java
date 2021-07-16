package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.bean.Resp;
import com.test.swagger.ApiGlobalModel;
import com.test.swagger.ApiJsonModel;
import com.test.swagger.ApiSimpleModel;
import com.test.model.po.GlobalPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * testApiGlobalModel
     *
     * @return R
     */
    @PostMapping("/testApiGlobalModel")
    @ApiOperation(value = "testApiGlobalModel")
    @ApiGlobalModel(component = GlobalPO.class, value = "userName,password")
    public Resp<Object> testApiGlobalModel(@RequestBody JSONObject param) {
        //***************************** 读取参数并校验 *******************************/
        String userName = param.getString("userName");
        String password = param.getString("password");
        //***************************** 处理业务并返回 *******************************/
        logger.info("userName={},password={}", userName, password);
        return Resp.success();
    }

    /**
     * testApiGlobalModel
     *
     * @return R
     */
    @PostMapping("/testApiJsonModel")
    @ApiOperation(value = "testApiJsonModel")
    @ApiJsonModel({
            @ApiModelProperty(name = "userName", value = "用户名"),
            @ApiModelProperty(name = "password", value = "密码")
    })
    public Resp<Object> testApiJsonModel(@RequestBody JSONObject param) {
        //***************************** 读取参数并校验 *******************************/
        String userName = param.getString("userName");
        String password = param.getString("password");
        //***************************** 处理业务并返回 *******************************/
        logger.info("userName={},password={}", userName, password);
        return Resp.success();
    }

    /**
     * testApiGlobalModel
     *
     * @return R
     */
    @PostMapping("/testApiSimpleModel")
    @ApiOperation(value = "testApiSimpleModel")
    @ApiSimpleModel("userName,password")
    public Resp<Object> testApiSimpleModel(@RequestBody JSONObject param) {
        //***************************** 读取参数并校验 *******************************/
        String userName = param.getString("userName");
        String password = param.getString("password");
        //***************************** 处理业务并返回 *******************************/
        logger.info("userName={},password={}", userName, password);
        return Resp.success();
    }
}