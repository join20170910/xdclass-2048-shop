package net.xdclass.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.mapper.UserMapper;
import net.xdclass.model.UserDO;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.INotifyService;
import net.xdclass.service.IUserService;
import net.xdclass.util.JsonData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author john
 * @since 2022-05-01
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {

    @Autowired
    private INotifyService notifyService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonData register(UserRegisterRequest userRegisterRequest) {

        boolean checkCode = false;
        //校验验证码
        checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, userRegisterRequest.getMail(), userRegisterRequest.getCode());
        if (!checkCode){
            JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }
        UserDO userDO = new UserDO();
        userDO.setCreateTime(new Date());
        userDO.setSlogan("hello word");
        //密码 TODO
        userDO.setPwd("");
        BeanUtils.copyProperties(userRegisterRequest,userDO);
        //账号唯一性检查 TODO
        if (checkUnique(userRegisterRequest.getMail())){
            int rows = userMapper.insert(userDO);
            log.info("用户注册成功影响行rows:[{}],注册用户信息[{}]",rows,userDO);
            // 新用户注册成功 初始化信息, 发放福利 TODO
            userRegisterInitTask(userDO);
            JsonData.buildSuccess();
        }else {
            JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }

        return null;
    }

    private boolean checkUnique(String mail) {
        return false;
    }

    /**
     * @Description: 用户注册 初始化信息, 发放福利 TODO
     * @Author: john
     * @Date: 2022/5/2 16:00:54
     * @version 1.0
     */
    private void userRegisterInitTask(UserDO userDO){

    }
}
