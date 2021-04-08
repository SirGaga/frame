package com.bstek.uflo.console.provider;

import org.dom4j.DocumentException;

import java.io.InputStream;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年7月12日
 */
public interface ProcessProvider {
	InputStream loadProcess(String fileName);
	List<ProcessFile> loadAllProcesses();
	void saveProcess(String fileName, String content) throws DocumentException;
	void deleteProcess(String fileName);
	String getName();
	String getPrefix();
	boolean support(String fileName);
	boolean isDisabled();
}
