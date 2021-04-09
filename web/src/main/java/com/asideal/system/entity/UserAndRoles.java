package com.asideal.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.asideal.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("re_user_role")
public class UserAndRoles extends BaseEntity {

    private Long userId;

    private Long roleId;
}
