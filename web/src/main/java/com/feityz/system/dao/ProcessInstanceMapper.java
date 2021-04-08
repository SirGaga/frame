package com.feityz.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.system.entity.ProcessInstanceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessInstanceMapper {

    List<ProcessInstanceVo> pageByAssignee(Page<ProcessInstanceVo> page, @Param("ew") ProcessInstanceVo condition);

    List<ProcessInstanceVo> pageByPromoter(Page<ProcessInstanceVo> page, @Param("ew") ProcessInstanceVo condition);

    List<ProcessInstanceVo> pageAll(Page<ProcessInstanceVo> page, @Param("ew") ProcessInstanceVo condition);

    void deleteHisInstance(Long instanceId);

    int selectInstanceByBizId(String bizId);

    int selectHisInstanceByBizId(String bizId);

    List<Integer> selectHisInstanceIdByBizId(String bizId);

    ProcessInstanceVo selectCurrentInstance(String bizId);


}
