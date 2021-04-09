package com.asideal.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.asideal.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单实体
 * @author zhangjie
 * @date 2021-04-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("re_role_menu")
public class RoleAndMenu extends BaseEntity {

    private Long roleId;

    private Long menuId;

    public static final String ROLE_ID = "role_id";

    public static final String MENU_ID = "menu_id";

}
