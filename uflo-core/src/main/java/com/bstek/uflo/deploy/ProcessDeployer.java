package com.bstek.uflo.deploy;

import com.bstek.uflo.model.ProcessDefinition;

import java.io.InputStream;

/**
 * @author Jacky.gao
 * @since 2013年7月30日
 */
public interface ProcessDeployer {
	public static final String BEAN_ID="uflo.processDeployer";
	public static final String PROCESS_EXTENSION_NAME=".uflo.xml";
	public static final String PROCESSIMG_EXTENSION_NAME=".png";
	ProcessDefinition deploy(InputStream inputStream);
	ProcessDefinition deploy(InputStream inputStream, int version, long processId);
}
