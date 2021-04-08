/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.uflo.model.task;

/**
 * @author Jacky.gao
 * @since 2013年7月25日
 */
public enum TaskState {
	/**
	 * 创建
	 */
	Created,
	/**
	 * 释放后变成ready状态
	 */
	Ready,
	Reserved,
	/**
	 * 任务start后变成inprogress状态
	 */
	InProgress,
	/**
	 * 完成状态
	 */
	Completed,
	/**
	 * 暂停
	 */
	Suspended,
	/**
	 * 关闭
	 */
	Canceled,
	/**
	 * 跳转
	 */
	Forwarded,
	/**
	 * 回退 A-B B操作为回退
	 */
	Rollback,
	/**
	 * 撤回 A-B A再操作为撤回
	 */
	Withdraw;
}
