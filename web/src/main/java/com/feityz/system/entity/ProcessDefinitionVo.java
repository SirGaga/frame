package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value="UFLO_PROCESS")
public class ProcessDefinitionVo implements java.io.Serializable {
    private static final long serialVersionUID = -1328642749306459546L;

    @TableId(value = "ID_")
    private long id;

    @TableField(value = "NAME_")
    private String name;

    @TableField(value = "KEY_")
    private String key;

    @TableField(value = "START_PROCESS_URL_")
    private String startProcessUrl;

    @TableField(value = "VERSION_")
    private int version;

    @TableField(value = "CREATE_DATE_")
    private Date createDate;

    @TableField(value = "EFFECT_DATE_")
    private Date effectDate;

    @TableField(value = "CATEGORY_ID_")
    private String categoryId;

    @TableField(value = "CATEGORY_")
    private String category;

    @TableField(value = "DESCRIPTION_")
    private String description;

    @TableField(exist = false)
    private boolean lastVersion = false;
}
