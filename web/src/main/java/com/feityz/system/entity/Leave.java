package com.feityz.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.feityz.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2020-05-15
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("biz_leave")
public class Leave extends BaseEntity {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;

    private String applyUser;

    private Integer draft;

    private Integer dayCount;

    private String position;


    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String APPLY_USER = "apply_user";

    public static final String DRAFT = "draft";


}
