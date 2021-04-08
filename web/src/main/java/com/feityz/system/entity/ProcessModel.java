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
 * @since 2020-05-20
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bas_process_model")
public class ProcessModel extends BaseEntity {

    private String processName;

    private String processKey;

    private String processType;

    private String fileName;

    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    public static final String PROCESS_NAME = "process_name";

    public static final String PROCESS_KEY = "process_key";

    public static final String PROCESS_TYPE = "process_type";

    public static final String FILE_NAME = "file_name";

    public static final String CREATE_TIME = "create_time";


}
