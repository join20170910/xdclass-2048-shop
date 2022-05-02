package net.xdclass.service;

import net.xdclass.enums.SendCodeEnum;
import net.xdclass.util.JsonData;

public interface INotifyService {
    JsonData sendCode(SendCodeEnum sendCodeEnum, String to);
    boolean checkCode(SendCodeEnum sendCodeEnum,String to,String code);
}
