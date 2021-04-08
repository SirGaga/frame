package com.feityz.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.model.task.Task;
import com.feityz.system.entity.TaskInfo;
import com.feityz.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    List<TaskInfo> listPage(Page<TaskInfo> page, @Param("ew") TaskInfo condition);

}
