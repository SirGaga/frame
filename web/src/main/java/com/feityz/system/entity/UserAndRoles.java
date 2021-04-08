package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.feityz.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("re_user_role")
public class UserAndRoles extends BaseEntity {

    private Long userId;

    private Long roleId;
}
