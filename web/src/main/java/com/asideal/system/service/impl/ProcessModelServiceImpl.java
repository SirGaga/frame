package com.asideal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.asideal.system.dao.ProcessModelMapper;
import com.asideal.system.service.IProcessModelService;
import com.asideal.system.entity.ProcessModel;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * ProcessModel服务类
 * </p>
 *
 * @author Line
 * @since 2020-05-20
 */
@Service
public class ProcessModelServiceImpl extends ServiceImpl<BaseMapper<ProcessModel>, ProcessModel> implements IProcessModelService {

    private final String FIX = "a.";

    @Autowired
    private ProcessModelMapper processModelMapper;

    @Override
    public IPage<ProcessModel> getPage(ProcessModel condition, Page<ProcessModel> page) {
        QueryWrapper<ProcessModel> query = new QueryWrapper<ProcessModel>();
        query.like(StringUtils.isNotEmpty(condition.getProcessName()), FIX + ProcessModel.PROCESS_NAME, condition.getProcessName());
        query.like(StringUtils.isNotEmpty(condition.getProcessKey()), FIX + ProcessModel.PROCESS_KEY, condition.getProcessKey());
        query.like(StringUtils.isNotEmpty(condition.getProcessType()), FIX + ProcessModel.PROCESS_TYPE, condition.getProcessType());
        query.like(StringUtils.isNotEmpty(condition.getFileName()), FIX + ProcessModel.FILE_NAME, condition.getFileName());
        query.eq(condition.getCreateTime() != null, FIX + ProcessModel.CREATE_TIME, condition.getCreateTime());
        query.eq(FIX + ProcessModel.FLAG, "1");
        IPage<ProcessModel> result = processModelMapper.listPage(page, query);
        return result;
    }

}