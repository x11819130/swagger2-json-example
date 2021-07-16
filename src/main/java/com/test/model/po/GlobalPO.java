package com.test.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 出参字段集合类 用于ApiGlobalModel
 * 不可继承 不可实例化 只是字段描述文件
 *
 * @author chengz
 * @date 2020/10/15
 */
@Data
@ApiModel
public abstract class GlobalPO implements Serializable {
    private GlobalPO() {
    }

    /**
     * 补充说明 - 检查补充说明
     */
    @ApiModelProperty("补充说明 - 检查补充说明")
    private String checkInfo;
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private Integer roomId;
    /**
     * 检查状态，0:待检查;1:检查不通过;2:检查通过
     */
    @ApiModelProperty("检查状态，1:检查不通过;2:检查通过")
    private Integer checkStatus;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id", name = "id")
    private Integer addressId;
    /**
     * 收件人电话
     */
    @ApiModelProperty(value = "收件人电话")
    private Integer telNumber;

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id", name = "id")
    private Integer cartId;
    /**
     * 商品Id
     */
    @ApiModelProperty("商品Id")
    private Integer goodsId;
    /**
     * sku_id
     */
    @ApiModelProperty("sku_id")
    private Integer skuId;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer number;
    /**
     * 已选规格列表
     */
    @ApiModelProperty("已选规格列表")
    private String properties;

    /**
     * 不同接口 相同入参名 但意义不同
     */
    @ApiModelProperty(name = "id", value = "用户id")
    private String userId;

    @ApiModelProperty(name = "id", value = "订单id")
    private String orderId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("短信验证码")
    private String smsCode;

    @ApiModelProperty("短信验证码Key")
    private String smsCodeKey;

    @ApiModelProperty("图形验证码Key")
    private String captchaKey;

    @ApiModelProperty("图形验证码")
    private String captchaCode;

    @ApiModelProperty(value = "短信业务类型", notes = "{\"0\":\"注册\",\"1\":\"登录\",\"2\":\"重置密码\",\"3\":\"绑定手机号\"}")
    private Integer busiType;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty(value = "新密码", name = "password")
    private String newPassword;

    @ApiModelProperty("微信code")
    private String code;

    @ApiModelProperty(name = "code", value = "支付宝code")
    private String aliCode;
}


