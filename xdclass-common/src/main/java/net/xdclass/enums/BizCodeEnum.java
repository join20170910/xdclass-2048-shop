package net.xdclass.enums;

import lombok.Getter;

/**
 * @Description: 统一业务状态码 BizCodeEnum开发
 * <p>- 状态码定义约束，共6位数，前三位代表服务，后3位代表接口 -
 * 比如 商品服务210,购物车是220、用户服务230，403代表权限
 *
 * @Author: john @Date:
 * 2022/5/1 22:39:47
 * @version 1.0
 */
public enum BizCodeEnum {

    /**
     * 通用操作码
     */
    OPS_REPEAT(110001,"重复操作"),

      /**
     *验证码
     */
    CODE_TO_ERROR(240001,"接收号码不合规"),
    CODE_LIMITED(240002,"验证码发送过快"),
    CODE_ERROR(240003,"验证码错误"),
    CODE_CAPTCHA(240101,"图形验证码错误"),

    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001,"账号已经存在"),
    ACCOUNT_UNREGISTER(250002,"账号不存在"),
    ACCOUNT_PWD_ERROR(250003,"账号或者密码错误");

    @Getter
    private String message;
    @Getter
    private int code;

    BizCodeEnum(int code,String message) {
        this.message = message;
        this.code = code;
    }
}