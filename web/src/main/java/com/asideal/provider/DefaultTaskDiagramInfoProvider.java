package com.asideal.provider;

import com.bstek.uflo.diagram.TaskDiagramInfoProvider;
import com.bstek.uflo.diagram.TaskInfo;
import com.asideal.system.entity.User;
import com.asideal.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangjie
 * @date 2021-04-09
 */
@Component
public class DefaultTaskDiagramInfoProvider implements TaskDiagramInfoProvider {
    @Resource
    private IUserService userService;

    private boolean disableDefaultTaskDiagramInfoProvider;
    @Override
    public boolean disable() {
        return false;
    }
    @Override
    public String getInfo(String nodeName, List<TaskInfo> tasks) {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb=null;
        if(tasks!=null && tasks.size()>0){
            sb=new StringBuilder();
            if(tasks.size()>1){
                for(int i=0;i<tasks.size();i++){
                    TaskInfo task=tasks.get(i);
                    sb.append("任务").append(i + 1).append(":\r");
                    User owner = new User();
                    if(StringUtils.isNotEmpty(task.getOwner())) {
                        owner = userService.getById(task.getOwner());
                        sb.append("所有人：").append(owner == null ? task.getOwner() : owner.getUserName() + "\r");
                    }
                    User assignee = new User();
                    if(StringUtils.isNotEmpty(task.getAssignee())) {
                        assignee = userService.getById(task.getAssignee());
                        sb.append("处理人：").append(assignee == null ? task.getAssignee() : assignee.getUserName() + "\r");
                    }

                    sb.append("创建时间：").append(sd.format(task.getCreateDate())).append("\r");
                    if(task.getEndDate()!=null){
                        sb.append("完成时间：").append(sd.format(task.getEndDate())).append("\r");
                    }
                }
            }else{
                String owner = "";
                String assignee="";
                TaskInfo task=tasks.get(0);
                if(StringUtils.isNotEmpty(task.getOwner())){
                    List<User> users = userService.lambdaQuery().in(User::getId,task.getOwner().split(",")).list();
                    if(users.size()>0){
                        owner = users.stream().map(User::getUserName).collect(Collectors.joining(","));
                    }
                }
                User assigneeUser = userService.getById(task.getAssignee());
                if(assigneeUser!=null){
                    assignee = assigneeUser.getUserName();
                }else{
                    assignee = task.getAssignee()==null?"":task.getAssignee();
                }

                sb.append("所有人：").append(owner).append("\r");
                sb.append("处理人：").append(assignee).append("\r");
                sb.append("创建时间：").append(sd.format(task.getCreateDate())).append("\r");
                if(task.getEndDate()!=null){
                    sb.append("完成时间：").append(sd.format(task.getEndDate())).append("\r");
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
