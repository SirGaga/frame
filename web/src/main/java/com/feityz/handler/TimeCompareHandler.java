package com.feityz.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component()
public class TimeCompareHandler implements DecisionHandler {
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        String time = context.getProcessService().getProcessVariable("time",processInstance.getId()).toString();
        try {
            Date varTime = DateUtils.parseDate(time,"yyyy-MM-dd hh:mm:ss");
            Date now = new Date();
            if(varTime.after(now)){
                return "大于";
            }else{
                return "小于";
            }

        }catch (Exception e){

        }
        return null;
    }

    @Override
    public String desc() {
        return "判断日期是否小于当前时间";
    }
}
