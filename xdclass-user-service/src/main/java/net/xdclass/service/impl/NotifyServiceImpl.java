package net.xdclass.service.impl;

import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.IMailService;
import net.xdclass.service.INotifyService;
import net.xdclass.util.CheckUtil;
import net.xdclass.util.CommonUtil;
import net.xdclass.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements INotifyService {

    private final String SUBJECT = "2048学习项目";
    private final String CONTENT ="您的验证码是%s,有效时间是60秒,打死也不要告诉别人!";
    @Autowired
    private IMailService mailService;
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {

        if(CheckUtil.isEmail(to)){
            String randomCode = CommonUtil.getRandomCode(6);
            //邮箱验证码
            mailService.sendSimpleMail(to,SUBJECT,String.format(CONTENT,randomCode));
            return JsonData.buildSuccess();

        }else if(CheckUtil.isPhone(to)){
            //短信验证码
        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }
    }
