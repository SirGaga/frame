package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.feityz.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2019-11-14
 */

/**
 * @author kongqinglin
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private String parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否子节点
     */
    private String leaf;

    private String url;

    private String permission;

    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    private boolean spread;

    /**
     * 是否被选中
     */
    @TableField(exist = false)
    private boolean checked;

    public static final String PARENT_ID = "parent_id";

    public static final String MENU_NAME = "menu_name";

    public static final String ICON = "icon";

    public static final String LEAF = "leaf";

    public static final String URL = "url";


}
