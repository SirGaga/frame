package com.asideal.system.dao;

import com.asideal.system.entity.ProcessModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Line
 * @since 2020-05-20
 */
public interface ProcessModelMapper extends BaseMapper<ProcessModel> {
    IPage<ProcessModel> listPage(IPage<ProcessModel> page, @Param("ew") QueryWrapper<ProcessModel> queryWrapper);
}
