package com.feityz.system.service;

import com.feityz.system.vo.CancelInput;
import com.feityz.system.vo.ProcessStartInput;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 流程运行接口
 * @author zhangjie
 * @date 2021-04-07
 */
public interface IProcessRunService {

    /**
     * 流程启动
     * @param input 流程启动输入
     * @return 返回启动参数
     */
    Map start(ProcessStartInput input);

    @Transactional
    void remove(long id);

    @Transactional
    void cancel(CancelInput input);

    Object getVaribale(long instancesId);
}
