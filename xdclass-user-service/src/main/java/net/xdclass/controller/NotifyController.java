package net.xdclass.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.INotifyService;
import net.xdclass.util.CommonUtil;
import net.xdclass.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1/")
@Slf4j
public class NotifyController {
    /**
     *临时使用10分钟有效，方便测试
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private StringRedisTemplate  redisTemplate;
    @Autowired
    private INotifyService notifyService;

    @ApiOperation("获取图型验证码")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String captchaText = captchaProducer.createText();
        log.info("图型验证码:[{}]",captchaText);
        BufferedImage image = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        String captchaKey = getCaptchaKey(request);
        redisTemplate.opsForValue().set(captchaKey,captchaText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("图型验证码获取失败原因[{}]",e);
        }
    }

    /**
     * 支持手机号、邮箱发送验证码
     * @return
     */
    @ApiOperation("发送验证码")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@ApiParam("收信人") @RequestParam(value = "to", required = true)String to,
                                     @ApiParam("图形验证码") @RequestParam(value = "captcha", required = true)String  captcha,
                                     HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
            JsonData jsonData = notifyService.sendCode(SendCodeEnum.USER_REGISTER,to);
            return jsonData;
        }else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }

    }

    /**
     * @Description: 获取缓存的key

     * @Author: john
     * @Date: 2022/5/2 09:40:05
     * @version 1.0
     */
    private String getCaptchaKey(HttpServletRequest request){
        String ipAddr = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:"+ CommonUtil.MD5(ipAddr + userAgent);
        log.info("缓存对象key缓存:[{}][{}][{}]",key,ipAddr,userAgent);
        return key;
    }
}
