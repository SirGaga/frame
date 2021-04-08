package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feityz.system.dao.LeaveMapper;
import com.feityz.system.service.ILeaveService;
import com.feityz.system.entity.Leave;
import lombok.var;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Leave服务类
 * </p>
 *
 * @author Line
 * @since 2020-05-15
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<BaseMapper<Leave>, Leave> implements ILeaveService {

    private final String FIX = "a.";

    @Override
    public IPage<Leave> getPage(Leave condition, Page<Leave> page) {

        var result = lambdaQuery()
                .eq(StringUtils.isNotEmpty(condition.getApplyUser()),Leave::getApplyUser,condition.getApplyUser())
                .eq(Leave::getDraft,"1")
                .page(page);
        return result;
    }

}