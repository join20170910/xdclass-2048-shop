package net.xdclass.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.IUserService;
import net.xdclass.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author john
 * @since 2022-05-01
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {
    @Autowired
    private IUserService userService;
@PostMapping("/register")
public JsonData register(@ApiParam("用户注册对象") @RequestBody UserRegisterRequest userRegisterRequest){
    return JsonData.buildSuccess(userService.register(userRegisterRequest));
}
}

