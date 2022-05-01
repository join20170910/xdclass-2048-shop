package net.xdclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.model.AddressDO;
import net.xdclass.mapper.AddressMapper;
import net.xdclass.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电商-公司收发货地址表 服务实现类
 * </p>
 *
 * @author john
 * @since 2022-05-01
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO> implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Override
    public AddressDO detail(Long id) {
        AddressDO id1 = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));
        log.info("参数及返回数据为：[{}],[{}]",id,id1);
        return id1;
    }
}
