package com.feityz.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptStatusNodeEventHandler implements NodeEventHandler {
    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        List<Variable> variables = context.getProcessService()
                .createProcessVariableQuery()
                .processInstanceId(processInstance.getRootId())
                .key("dept").list();

        System.out.println("进入聚合节点");
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {

    }

    @Override
    public String desc() {
        return "回传部门完成情况";
    }
}
