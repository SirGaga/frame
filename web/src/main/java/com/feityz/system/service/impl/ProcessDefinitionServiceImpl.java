package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.ProcessDefinitionMapper;
import com.feityz.system.entity.ProcessDefinitionVo;
import com.feityz.system.service.IProcessDefinitionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ProcessDefinitionServiceImpl extends ServiceImpl<ProcessDefinitionMapper, ProcessDefinitionVo>
        implements IProcessDefinitionService {
    @Override
    public IPage<ProcessDefinitionVo> definitionPage(Page<ProcessDefinitionVo> page, ProcessDefinitionVo condition) {

        IPage<ProcessDefinitionVo> result = this.lambdaQuery()
                .eq(StringUtils.isNotEmpty(condition.getName()),
                        ProcessDefinitionVo::getName,condition.getName())
                .eq(StringUtils.isNotEmpty(condition.getDescription()),
                        ProcessDefinitionVo::getDescription,condition.getDescription())
                .eq(StringUtils.isNotEmpty(condition.getCategory()),
                        ProcessDefinitionVo::getCategory,condition.getCategory())
                .inSql(condition.isLastVersion(),ProcessDefinitionVo::getId,
                        "select ID_ from uflo_process res " +
                                "where res.VERSION_ = (select max(VERSION_) from " +
                                "uflo_process where KEY_ = res.KEY_)")
                .page(page);
        return result;
    }
}
