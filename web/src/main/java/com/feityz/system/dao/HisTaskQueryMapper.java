package com.feityz.system.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.system.entity.HistoryTaskInfo;
import com.feityz.system.entity.TaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HisTaskQueryMapper extends BaseMapper<HistoryTaskInfo> {
    /**
     * 根据流程实例查找历史任务
     * @param
     * @return
     */
    List<HistoryTaskInfo> findHistoryTask(long instanceId);


    /**
     * 已办记录
     * @param page
     * @param condition
     * @return
     */
    List<HistoryTaskInfo> listPage(Page<HistoryTaskInfo> page, @Param("ew") HistoryTaskInfo condition);
}
