package net.xdclass.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xdclass.service.ICouponService;
import net.xdclass.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author john
 * @since 2022-05-04
 */

@Api("优惠劵模块")
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    @Autowired
    private ICouponService couponService;
    @ApiOperation("分页查询优惠劵")
    @GetMapping("page_coupon")
    public JsonData pageCouponList(
            @ApiParam(value = "当前页") @RequestParam(value = "page",defaultValue = "1") int page,
            @ApiParam(value = "每页显示记录数") @RequestParam(value = "size",defaultValue = "10") int size
    ){
return JsonData.buildSuccess(couponService.pageCouponActivity(page,size));
    }
}

