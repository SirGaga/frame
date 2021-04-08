package com.feityz.system.dao;

import com.feityz.system.entity.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Line
 * @since 2020-05-15
 */
public interface LeaveMapper extends BaseMapper<Leave> {
        IPage<Leave> listPage(IPage<Leave> page,@Param("ew") QueryWrapper<Leave> queryWrapper);
        }
