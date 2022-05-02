package net.xdclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.mapper.UserMapper;
import net.xdclass.model.LoginUser;
import net.xdclass.model.UserDO;
import net.xdclass.request.UserLoginRequest;
import net.xdclass.request.UserRegisterRequest;
import net.xdclass.service.INotifyService;
import net.xdclass.service.IUserService;
import net.xdclass.util.CommonUtil;
import net.xdclass.util.JWTUtil;
import net.xdclass.util.JsonData;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        BeanUtils.copyProperties(userRegisterRequest,userDO);
        userDO.setCreateTime(new Date());
        userDO.setSlogan("hello word");
        //密码 TODO
        // 生成秘钥 盐
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        String cryptPwd = Md5Crypt.md5Crypt(userDO.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);


        //账号唯一性检查 TODO
        if (checkUnique(userRegisterRequest.getMail())){
            int rows = userMapper.insert(userDO);
            log.info("用户注册成功影响行rows:[{}],注册用户信息[{}]",rows,userDO);
            // 新用户注册成功 初始化信息, 发放福利 TODO
            userRegisterInitTask(userDO);
            return JsonData.buildSuccess();
        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    @Override
    public JsonData login(UserLoginRequest loginRequest) {

        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("mail", loginRequest.getMail()));
        if (userDO != null ){
            String pwd = userDO.getPwd();
            String crypt = Md5Crypt.md5Crypt(loginRequest.getPwd().getBytes(), userDO.getSecret());
            if (pwd.equals(crypt)){
                // 登陆成功 生成token
                LoginUser userDTO = new LoginUser();
                BeanUtils.copyProperties(userDO, userDTO);
                String token = JWTUtil.geneJsonWebToken(userDTO);
                return JsonData.buildSuccess(token);

            }else {
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        }else {
            JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
        return null;
    }

    private boolean checkUnique(String mail) {
        List<UserDO> userList = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", mail));
        if (userList !=null && userList.size()>0){
            return false;
        }else {
            return true;
        }
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
