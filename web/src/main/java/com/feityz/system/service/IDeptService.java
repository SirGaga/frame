package com.feityz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feityz.system.entity.Dept;

import java.util.List;

/**
 * <p>
 * Dept服务接口
 * </p>
 *
 * @author Line
 * @since 2019-11-17
 */
public interface IDeptService extends IService<Dept> {

    public IPage<Dept> getPage(Dept condition, Page<Dept> page);

    public List<Dept> listAll(Dept dept);

    public boolean checkDept(Dept dept);

    void saveDeptSync(Dept entity);
}
