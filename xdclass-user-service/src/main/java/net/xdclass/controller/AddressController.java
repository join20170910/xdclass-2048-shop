package net.xdclass.controller;


import net.xdclass.model.AddressDO;
import net.xdclass.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author john
 * @since 2022-05-01
 */
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {
    @Autowired
private IAddressService addressService;

    @GetMapping("detail/{address_id}")
    public Object detail(@PathVariable("address_id") Long addressId){
        AddressDO detail = addressService.detail(addressId);
        return detail;
    }
}

