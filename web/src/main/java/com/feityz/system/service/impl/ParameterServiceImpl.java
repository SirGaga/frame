package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.ParameterMapper;
import com.feityz.system.entity.Parameter;
import com.feityz.system.service.IParameterService;
import exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Parameter服务类
 * </p>
 *
 * @author Line
 * @since 2019-11-28
 */
@Service
public class ParameterServiceImpl extends ServiceImpl<ParameterMapper, Parameter> implements IParameterService {


    @Override
    public IPage<Parameter> getPage(Parameter condition, Page<Parameter> page) {
        QueryWrapper<Parameter> query = new QueryWrapper<Parameter>();
        if (StringUtils.isNotEmpty(condition.getCode())) {
            query.like("a." + Parameter.CODE, condition.getCode());
        }
        if (StringUtils.isNotEmpty(condition.getName())) {
            query.like("a." + Parameter.NAME, condition.getName());
        }
        if (StringUtils.isNotEmpty(condition.getDesc())) {
            query.like("a." + Parameter.DESC, condition.getDesc());
        }
        if (StringUtils.isNotEmpty(condition.getValue())) {
            query.like("a." + Parameter.VALUE, condition.getValue());
        }
        query.eq(Parameter.FLAG, "1");
        IPage<Parameter> result = baseMapper.listPage(page, query);
        return result;
    }

    @Override
    public Parameter getByCode(String code) {
        QueryWrapper<Parameter> query = new QueryWrapper<Parameter>();
        query.eq(Parameter.CODE, code);
        Parameter parameter = baseMapper.selectOne(query);
        if (parameter == null) {
            parameter = new Parameter();
        }
        return parameter;
    }

    @Override
    public boolean checkParameter(Parameter parameter) {
        QueryWrapper<Parameter> query = new QueryWrapper<Parameter>();
        query.eq(Parameter.CODE, parameter.getCode());
        List<Parameter> parameters = baseMapper.selectList(query);
        //插入时候
        if (parameter.getId() == null) {
            if (parameters != null && parameters.size() > 0) {
                throw new BizException("已存在编号为[" + parameter.getCode() + "]的参数");
            }
        } else {
            //更新操作
            if (parameters.size() == 1) {
                if (!parameter.getId().equals(parameters.get(0).getId())) {
                    throw new BizException("已存在编号为[" + parameter.getCode() + "]的参数");
                }
            }
        }
        return true;
    }
}
