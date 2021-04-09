package com.asideal.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.asideal.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> listPage(Page<User> page, @Param("u") User condition);

    List<User> listPage(@Param("u") User condition);

    void deletePhysical(Long id);
}
