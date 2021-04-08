package com.feityz.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feityz.system.entity.Parameter;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Line
 * @since 2019-11-28
 */
public interface ParameterMapper extends BaseMapper<Parameter> {
    IPage<Parameter> listPage(IPage<Parameter> page, @Param("ew") QueryWrapper<Parameter> queryWrapper);
}
