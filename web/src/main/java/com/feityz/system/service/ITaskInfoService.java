package com.feityz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bstek.uflo.process.security.ComponentAuthority;
import com.feityz.system.entity.TaskInfo;
import com.feityz.system.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ITaskInfoService extends IService<TaskInfo> {

    IPage<TaskInfo> getPage(TaskInfo condition, Page<TaskInfo> page);

    /**
     * 完成一个任务
     * @param input
     * @return
     */
    Map completeTask(TaskInput input);

    /**
     * 根据taskId获取下一步需要设置人员的node
     * @param input
     * @return
     */
    List<NextNodeVo> getNextNodeByTaskId(GetNextNodeInput input);

    List<NextNodeVo> getNextNodeNormal(GetNextNodeInput input);

    /**
     * 驳回上一节点
     * @param input
     */
    void rollBack(RollBackInput input);

    /**
     * 驳回开始节点
     * @param input
     */
    void rollBackStart(RollBackInput input);

    void removeProcessByTaskId(long taskId);

    /**
     * 驳回开始节点
     * @param input
     */
    void changeAssignee(ChangeAssigneeInput input);

    @Transactional
    void reject(RollBackInput input);

    List<ComponentAuthority> taskAuth(Long taskId,String key);

    void setNextAssignee(GetNextNodeInput input);
}
