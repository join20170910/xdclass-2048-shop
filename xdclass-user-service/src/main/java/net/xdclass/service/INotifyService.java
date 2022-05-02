package net.xdclass.service;

import net.xdclass.enums.SendCodeEnum;
import net.xdclass.util.JsonData;

public interface INotifyService {
    JsonData sendCode(SendCodeEnum sendCodeType, String to);
}
