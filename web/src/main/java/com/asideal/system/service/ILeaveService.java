package com.asideal.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.asideal.system.entity.Leave;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Leave服务接口
 * </p>
 *
 * @author Line
 * @since 2020-05-15
 */
public interface ILeaveService extends IService<Leave> {

    public IPage<Leave> getPage(Leave condition, Page<Leave> page);

}