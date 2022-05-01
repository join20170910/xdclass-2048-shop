package net.xdclass.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "收货地址模块")
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {
    @Autowired
private IAddressService addressService;

    @ApiOperation("根据id查询地址详情")
    @GetMapping("detail/{address_id}")
    public Object detail(@ApiParam(value = "地址id",required = true,type = "Long") @PathVariable("address_id") Long addressId){
        AddressDO detail = addressService.detail(addressId);
        return detail;
    }
}

