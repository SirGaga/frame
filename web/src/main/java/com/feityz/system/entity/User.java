package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseEntity {
    private String userName;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @TableField(exist = false)
    private String oldPassword;

    private String userNum;

    private Long dept;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String deptNum;

    @TableField(exist = false)
    private String roles;

}
