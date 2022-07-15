package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.NationMapper;
import com.sudongzhao.server.pojo.Nation;
import com.sudongzhao.server.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
