package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.feityz.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2019-11-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String roleName;

    private String roleNum;

    @TableField(exist = false)
    private String userId;

    public static final String ROLE_NAME = "role_name";

    public static final String ROLE_NUM = "role_num";
    @TableField(exist = false)
    private String menus;

}
