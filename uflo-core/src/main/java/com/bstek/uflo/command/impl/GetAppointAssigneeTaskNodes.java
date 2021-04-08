package com.bstek.uflo.command.impl;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.expr.ExpressionContext;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.flow.SequenceFlowImpl;
import com.bstek.uflo.process.node.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 孔庆麟扩展
 */
public class GetAppointAssigneeTaskNodes implements Command<List<String>> {
    public Task task;
    public GetAppointAssigneeTaskNodes(Task task){
        this.task=task;
    }
    private ExpressionContext expressionContext;

    public void setExpressionContext(ExpressionContext context){
        this.expressionContext = context;
    }

    @Override
    public List<String> execute(Context context) {
        this.expressionContext = context.getExpressionContext();
        ProcessDefinition pd=context.getProcessService().getProcessById(task.getProcessId());
        Node node=pd.getNode(task.getNodeName());
        List<String> nodes=new ArrayList<String>();
        getAvliableNodes(node,nodes,pd);
        return nodes;
    }
    private void getAvliableNodes(Node node,List<String> nodes,ProcessDefinition pd){
        List<SequenceFlowImpl> flows=node.getSequenceFlows();
        if(flows==null || flows.size()==0) {
            return;
        }
        //判断是否是路由决策节点
        if(node instanceof DecisionNode){
            DecisionNode decisionNode = (DecisionNode)node;
            Object obj = expressionContext.eval(task.getProcessInstanceId(),decisionNode.getExpression());
            flows = flows.stream().filter(n->n.getName().equals(obj)).collect(Collectors.toList());
        }

        if(node instanceof ForeachNode){
            ForeachNode foreachNode = (ForeachNode)node;
            flows = foreachNode.getSequenceFlows();
        }

        for(SequenceFlowImpl flow:flows){
            String flowName = flow.getName();
            if(StringUtils.isNotEmpty(flowName)) {
                if (flowName.indexOf("temp_flow") > 0) {
                    continue;
                }
            }
            Node toNode=pd.getNode(flow.getToNode());

            if(toNode instanceof StartNode ||
               //toNode instanceof JoinNode ||
               //toNode instanceof ForeachNode ||
               toNode instanceof SubprocessNode ||
               toNode instanceof ForkNode){
                toNode.setEnterFlow(flowName);
                continue;
            }
            if(toNode instanceof TaskNode){
                toNode.setEnterFlow(flowName);
                nodes.add(toNode.getName());
            } else{

                getAvliableNodes(toNode,nodes,pd);
            }
        }
    }
}
