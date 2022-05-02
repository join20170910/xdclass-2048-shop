package net.xdclass.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private StringRedisTemplate  stringRedisTemplate;
    @ApiOperation("获取图型验证码")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String captchaText = captchaProducer.createText();
        log.info("图型验证码:[{}]",captchaText);
        BufferedImage image = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        String captchaKey = getCaptchaKey(request);
        stringRedisTemplate.opsForValue().set(captchaKey,captchaText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);
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
