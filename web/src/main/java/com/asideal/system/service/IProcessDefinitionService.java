package com.asideal.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.asideal.system.entity.ProcessDefinitionVo;

public interface IProcessDefinitionService extends IService<ProcessDefinitionVo> {

    IPage<ProcessDefinitionVo> definitionPage(Page<ProcessDefinitionVo>page ,ProcessDefinitionVo condition);

}
