package net.xdclass.service;

import net.xdclass.request.UserRegisterRequest;
import net.xdclass.util.JsonData;

public interface IUserService {

  /**
   * @Description: - 邮箱验证码验证 - 密码加密（TODO） - 账号唯一性检查(TODO) - 插入数据库 - 新注册用户福利发放(TODO)
   *
   * @Author:john
   * @Date: 2022/5/2 15:37:33
   * @version 1.0
   */
  JsonData register(UserRegisterRequest userRegisterRequest);
}
