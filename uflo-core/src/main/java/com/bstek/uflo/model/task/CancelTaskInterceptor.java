package com.bstek.uflo.model.task;

import com.bstek.uflo.env.Context;

/**
 * @author Jacky.gao
 * @since 2013年7月31日
 */
public interface CancelTaskInterceptor {
	void intercept(Context context, Task task);
}
