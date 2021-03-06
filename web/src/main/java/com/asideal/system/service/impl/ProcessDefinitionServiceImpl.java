package com.asideal.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.asideal.system.dao.ProcessDefinitionMapper;
import com.asideal.system.entity.ProcessDefinitionVo;
import com.asideal.system.service.IProcessDefinitionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 流程定义
 * @author family
 * @date 2021-04-08
 */
@Service
public class ProcessDefinitionServiceImpl extends ServiceImpl<ProcessDefinitionMapper, ProcessDefinitionVo>
        implements IProcessDefinitionService {
    @Override
    public IPage<ProcessDefinitionVo> definitionPage(Page<ProcessDefinitionVo> page, ProcessDefinitionVo condition) {

        return this.lambdaQuery()
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
    }
}
