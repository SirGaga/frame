package com.asideal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.asideal.system.dao.DeptMapper;
import com.asideal.system.entity.Dept;
import com.asideal.system.entity.User;
import com.asideal.system.service.IDeptService;
import exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Dept服务类
 * </p>
 *
 * @author Line
 * @since 2019-11-17
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public IPage<Dept> getPage(Dept condition, Page<Dept> page) {
        return lambdaQuery().eq(StringUtils.isNotEmpty(condition.getDeptNum()),Dept::getDeptNum,condition.getDeptNum())
                .like(StringUtils.isNotEmpty(condition.getDeptName()),Dept::getDeptName,condition.getDeptName())
                .page(page);
    }

    @Override
    public List<Dept> listAll(Dept dept) {
        QueryWrapper<Dept> query = new QueryWrapper<Dept>();
        User user = new User();
        return baseMapper.selectList(query);

    }

    @Override
    public boolean checkDept(Dept dept) {
        QueryWrapper<Dept> query = new QueryWrapper<Dept>();
        query.eq(Dept.DEPT_NUM, dept.getDeptNum());
        List<Dept> depts = baseMapper.selectList(query);
        //插入时候
        if (dept.getId() == null) {
            if (depts != null && depts.size() > 0) {
                throw new BizException("已存在编号为[" + dept.getDeptNum() + "]的部门");
            }
        } else {
            //更新操作
            if (depts.size() == 1) {
                if (!dept.getId().equals(depts.get(0).getId())) {
                    throw new BizException("已存在编号为[" + dept.getDeptNum() + "]的部门");
                }
            }
        }

        return true;
    }

    @Override
    public void saveDeptSync(Dept entity){
        String deptNum = entity.getDeptNum();
        Dept dept = this.lambdaQuery().eq(Dept::getDeptNum,deptNum).one();

        if(dept==null){
            dept = new Dept();
            dept.setDeptNum(deptNum);
            dept.setDeptName(entity.getDeptName());
            this.saveOrUpdate(dept);
        }else{
            dept.setDeptName(entity.getDeptName());
            this.saveOrUpdate(dept);
        }
    }

}
