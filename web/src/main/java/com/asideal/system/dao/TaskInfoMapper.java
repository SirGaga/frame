package com.asideal.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.asideal.system.entity.TaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    List<TaskInfo> listPage(Page<TaskInfo> page, @Param("ew") TaskInfo condition);

}
