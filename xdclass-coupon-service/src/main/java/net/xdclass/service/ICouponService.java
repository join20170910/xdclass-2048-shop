package net.xdclass.service;

import net.xdclass.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author john
 * @since 2022-05-04
 */
public interface ICouponService extends IService<CouponDO> {
  Map<String,Object> pageCouponActivity(int page, int size);
}
