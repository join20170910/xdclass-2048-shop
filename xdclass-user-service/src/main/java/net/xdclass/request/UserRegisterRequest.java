package net.xdclass.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户注册对象",description = "用户注册请求对象")
@Data
public class UserRegisterRequest {
 @ApiModelProperty(value = "昵称",example = "Anna小姐姐")
    private String name;
    @ApiModelProperty(value = "密码",example = "123456")
    private String pwd;
    @ApiModelProperty(value = "头像",example = "https://sfsdf/adfdf.img")
    private String headImg;
    @ApiModelProperty(value = "用户个性签名",example = "人生需要动态规划")
    private String slogan;
    @ApiModelProperty(value = "0表示女,1表示男",example = "Anna小姐姐")
    private Integer sex;
    @ApiModelProperty(value = "邮箱",example = "2323@qq.com")
    private String mail;
    @ApiModelProperty(value = "验证码",example = "123456")
    private String code;

}
