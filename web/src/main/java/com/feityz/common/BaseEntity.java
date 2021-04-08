package com.feityz.common;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 基础实体类
 * @author family
 * @date 2021-04-07
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 分布式全局唯一主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "insert_user", fill = FieldFill.INSERT)
    @JsonIgnore
    private String insertUser;

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    @JsonIgnore
    private String updateUser;

    /**
     * @ JsonIgnore
     */
    @TableField(value = "insert_time", fill = FieldFill.INSERT)
    private Date insertTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonIgnore
    private Date updateTime;


    @TableField(value = "`flag`", fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private boolean flag;

    /**
     * 主键
     */
    public static final String ID = "id";

    /**
     * 状态 1正常 0 删除
     */
    public static final String FLAG = "`flag`";

    /**
     * 创建人员
     */
    public static final String INSERT_USER = "insert_user";

    /**
     * 更新人员
     */
    public static final String UPDATE_USER = "update_user";

    /**
     * 创建时间
     */
    public static final String INSERT_TIME = "insert_time";

}
