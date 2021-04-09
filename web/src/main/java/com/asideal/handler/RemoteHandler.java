package com.asideal.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RemoteHandler implements NodeEventHandler {
    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        String url = node.getDescription();
        url = url+"/enter";
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
        //将表单由草稿状态改成流程中的状态
        String url = node.getDescription();
        url = url+"/leave";
        //String url = context.getProcessService().getProcessVariable("startUrl",processInstance).toString();
        if(StringUtils.isEmpty(url)){
            return;
        }else{
            RestTemplate restTemplate = new RestTemplate();
            Map parms = new HashMap();
            parms.put("id",processInstance.getBusinessId());
            try {
                restTemplate.postForEntity(url,parms,Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public String desc() {
        return "调用远程方法,请在描述中填入远程url地址";
    }
}
