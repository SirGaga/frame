package com.asideal.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.asideal.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_parameter")
public class Parameter extends BaseEntity {

    @TableField("`code`")
    private String code;
    @TableField("`name`")
    private String name;
    @TableField("`desc`")
    private String desc;
    @TableField("`value`")
    private String value;


    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String DESC = "desc";

    public static final String VALUE = "value";


}
