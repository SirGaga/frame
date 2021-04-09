package com.asideal.system.entity;

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
 * @since 2019-11-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
public class Dept extends BaseEntity {

    private String deptNum;

    private String deptName;


    public static final String DEPT_NUM = "dept_num";

    public static final String DEPT_NAME = "dept_name";


}
