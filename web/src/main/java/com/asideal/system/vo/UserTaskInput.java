package com.asideal.system.vo;

import lombok.Data;
import java.util.Date;

/**
 * @author kongqinglin
 */
@Data
public class UserTaskInput {

    private String subject;

    private Date starttime;

    private Date endtime;

    private String userNum;

    private String processKey;

    private String taskType;

    private int page;

    private int limit;

}
