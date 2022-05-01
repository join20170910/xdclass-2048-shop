package net.xdclass.exception;

import lombok.Data;
import net.xdclass.enums.BizCodeEnum;

/**
 * @Description: 全局异常处理
 * @Author: john
 * @Date: 2022/5/1 22:50:23
 * @version 1.0
 */
@Data
public class BizException extends RuntimeException {

    private Integer code;
    private String msg;

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

}