package net.xdclass.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户登陆对象",description = "用户登陆请求对象")
@Data
public class UserLoginRequest {
    @ApiModelProperty(value = "邮箱",example = "181881@qq.com")
    private String mail;
    @ApiModelProperty(value = "密码",example = "123456")
    private String pwd;
}
