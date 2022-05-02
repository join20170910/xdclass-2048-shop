package net.xdclass.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.IMailService;
import net.xdclass.service.INotifyService;
import net.xdclass.util.CheckUtil;
import net.xdclass.util.CommonUtil;
import net.xdclass.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NotifyServiceImpl implements INotifyService {

    private final String SUBJECT = "注册验证码";
    private final String CONTENT ="您的验证码是%s,有效时间是60秒,打死也不要告诉别人!";
    @Autowired
    private IMailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @Description: 发送验证码     方法：【基于key 拼接时间戳】《保存原子性》
     * 前置条件 ：判断是否重复发送
     *  1、存储验证码到缓存
     *  2、发送验证码到邮箱
     *  后置条件： 存储发送记录
     *
     * @Author: john
     * @Date: 2022/5/2 12:57:58
     * @version 1.0
     */
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeType, String to) {


        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);

        //如果不为空，则判断是否60秒内重复发送
        if(StringUtils.isNotBlank(cacheValue)){
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            //当前时间戳-验证码发送时间戳，如果小于60秒，则不给重复发送
            if(CommonUtil.getCurrentTimestamp() - ttl < 1000*60){
                log.info("重复发送验证码,时间间隔:{} 秒",(CommonUtil.getCurrentTimestamp()-ttl)/1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }

        //拼接验证码 2322_324243232424324
        String code = CommonUtil.getRandomCode(6);

        String value = code+"_"+CommonUtil.getCurrentTimestamp();

        redisTemplate.opsForValue().set(cacheKey,value,CODE_EXPIRED, TimeUnit.MILLISECONDS);

        if(CheckUtil.isEmail(to)){
            //邮箱验证码
            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));

            return JsonData.buildSuccess();

        } else if(CheckUtil.isPhone(to)){
            //短信验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }
    }
