package com.feityz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feityz.system.service.IProcessModelService;
import com.feityz.system.entity.ProcessModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ProcessModel服务接口
 * </p>
 *
 * @author Line
 * @since 2020-05-20
 */
public interface IProcessModelService extends IService<ProcessModel> {

    public IPage<ProcessModel> getPage(ProcessModel condition, Page<ProcessModel> page);

}