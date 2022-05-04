package net.xdclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.xdclass.enums.CouponCategoryEnum;
import net.xdclass.enums.CouponPublishEnum;
import net.xdclass.model.CouponDO;
import net.xdclass.mapper.CouponMapper;
import net.xdclass.service.ICouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xdclass.vo.CouponVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author john
 * @since 2022-05-04
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements ICouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {
            //第1页，每页2条
            Page<CouponDO> pageInfo = new Page<>(page, size);
            IPage<CouponDO> couponDOPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>()
                    .eq("publish", CouponPublishEnum.PUBLISH)
                    .eq("category", CouponCategoryEnum.PROMOTION)
                    .orderByDesc("create_time"));

            Map<String, Object> pageMap = new HashMap<>(3);

            pageMap.put("total_record", couponDOPage.getTotal());
            pageMap.put("total_page", couponDOPage.getPages());
            pageMap.put("current_data", couponDOPage.getRecords().stream().map(obj -> beanProcess(obj)).collect(Collectors.toList()));

            return pageMap;
    }

    private CouponVo beanProcess(CouponDO couponDO) {
        final CouponVo couponVo = new CouponVo();
        BeanUtils.copyProperties(couponDO,couponVo);
        return couponVo;
    }
}
