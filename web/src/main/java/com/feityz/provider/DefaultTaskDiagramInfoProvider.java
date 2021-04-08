package com.feityz.provider;

import com.bstek.uflo.diagram.TaskDiagramInfoProvider;
import com.bstek.uflo.diagram.TaskInfo;
import com.feityz.system.entity.User;
import com.feityz.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultTaskDiagramInfoProvider implements TaskDiagramInfoProvider {
    @Autowired
    private IUserService userService;

    private boolean disableDefaultTaskDiagramInfoProvider;
    @Override
    public boolean disable() {
        return false;
    }
    @Override
    public String getInfo(String nodeName, List<TaskInfo> tasks) {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb=null;
        if(tasks!=null && tasks.size()>0){
            sb=new StringBuffer();
            if(tasks.size()>1){
                for(int i=0;i<tasks.size();i++){
                    TaskInfo task=tasks.get(i);
                    sb.append("任务"+(i+1)+":\r");
                    User owner = new User();
                    if(StringUtils.isNotEmpty(task.getOwner())) {
                        owner = userService.getById(task.getOwner());
                        sb.append("所有人："+(owner==null?task.getOwner():owner.getUserName()+"\r"));
                    }
                    User assignee = new User();
                    if(StringUtils.isNotEmpty(task.getAssignee())) {
                        assignee = userService.getById(task.getAssignee());
                        sb.append("处理人："+(assignee==null?task.getAssignee():assignee.getUserName()+"\r"));
                    }

                    sb.append("创建时间："+sd.format(task.getCreateDate())+"\r");
                    if(task.getEndDate()!=null){
                        sb.append("完成时间："+sd.format(task.getEndDate())+"\r");
                    }
                }
            }else{
                String owner = "";
                String assignee="";
                TaskInfo task=tasks.get(0);
                if(StringUtils.isNotEmpty(task.getOwner())){
                    List<User> users = userService.lambdaQuery().in(User::getId,task.getOwner().split(",")).list();
                    if(users.size()>0){
                        owner = users.stream().map(x->x.getUserName()).collect(Collectors.joining(","));
                    }
                }
                User assigneeUser = userService.getById(task.getAssignee());
                if(assigneeUser!=null){
                    assignee = assigneeUser.getUserName();
                }else{
                    assignee = task.getAssignee()==null?"":task.getAssignee();
                }

                sb.append("所有人："+owner+"\r");
                sb.append("处理人："+assignee+"\r");
                sb.append("创建时间："+sd.format(task.getCreateDate())+"\r");
                if(task.getEndDate()!=null){
                    sb.append("完成时间："+sd.format(task.getEndDate())+"\r");
                }
            }
        }
        if(sb!=null){
            return sb.toString();
        }else{
            return null;
        }
    }

    public void setDisableDefaultTaskDiagramInfoProvider(
            boolean disableDefaultTaskDiagramInfoProvider) {
        this.disableDefaultTaskDiagramInfoProvider = disableDefaultTaskDiagramInfoProvider;
    }
}
