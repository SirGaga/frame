package com.feityz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feityz.system.entity.HistoryTaskInfo;

import java.util.List;

public interface IHisTaskQueryService extends IService<HistoryTaskInfo> {

    List<HistoryTaskInfo> findHistoryByTaskId(Long taskId);

    List<HistoryTaskInfo> findHistoryByInstanceId(Long instanceId);

    IPage<HistoryTaskInfo> getPage(HistoryTaskInfo condition, Page<HistoryTaskInfo> page);
}
