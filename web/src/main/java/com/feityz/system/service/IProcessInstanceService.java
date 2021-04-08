package com.feityz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.system.entity.ProcessInstanceVo;
import com.feityz.system.vo.CurrentNodeVo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangjie
 * @date 2021-04-07
 */
public interface IProcessInstanceService {
    IPage<ProcessInstanceVo> pageByAssignee(ProcessInstanceVo condition, Page<ProcessInstanceVo> page);

    IPage<ProcessInstanceVo> pageByPromoter(ProcessInstanceVo condition, Page<ProcessInstanceVo> page);

    IPage<ProcessInstanceVo> pageAll(ProcessInstanceVo condition, Page<ProcessInstanceVo> page);

    void deleteHisInstance(Long instanceId);

    Map getInstanceParams(long instanceId);

    List<CurrentNodeVo> getCurrent(Long bizId);
}
