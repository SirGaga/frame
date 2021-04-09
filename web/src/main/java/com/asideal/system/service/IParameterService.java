package com.asideal.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.asideal.system.entity.Parameter;

/**
 * <p>
 * Parameter服务接口
 * </p>
 *
 * @author Line
 * @since 2019-11-28
 */
public interface IParameterService extends IService<Parameter> {

    IPage<Parameter> getPage(Parameter condition, Page<Parameter> page);

    Parameter getByCode(String code);

    boolean checkParameter(Parameter parameter);
}
