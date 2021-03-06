package com.bstek.uflo.process.assign;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;

import java.util.Collection;


/**
 * @author Jacky.gao
 * @since 2013年8月17日
 */
public interface AssigneeProvider {
	/**
	 * 设计器层面是否要用树形结构进行展示
	 * @return 返回true，表示设计器会用树形加载当前任务处理人列表
	 */
	boolean isTree();
	/**
	 * 获取当前任务处理人提供者名称，比如员工列表，部门列表等
	 * @return 返回当前任务处理人提供者名称，比如员工列表，部门列表等
	 */
	String getName();
	/**
	 * 分页方式查询返回具体的任务处理人，可以是具体的人，也可以是部门等之类容器型对象
	 * @param pageQuery 用于包装分页信息的查询对象
	 * @param parentId 上级实体对象的ID，可能为空
	 */
	void queryEntities(PageQuery<Entity> pageQuery, String parentId);
	/**
	 * 根据指定的处理人ID，返回具体的任务处理人用户名
	 * @param entityId 处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等 
	 * @param context context 流程上下文对象
	 * @param processInstance 流程实例对象
	 * @return 返回一个或多个任务处理人的ID
	 */
	Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance);
	/**
	 * 是否禁用当前任务处理人提供器
	 * @return 是否禁用当前任务处理人提供器
	 */
	boolean disable();
}
