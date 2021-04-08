package com.bstek.uflo.deploy.impl;

import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.DeployProcessCommand;
import com.bstek.uflo.command.impl.DeployProcessResourceCommand;
import com.bstek.uflo.deploy.ProcessDeployer;
import com.bstek.uflo.deploy.parse.impl.ProcessParser;
import com.bstek.uflo.deploy.validate.ProcessValidateException;
import com.bstek.uflo.deploy.validate.impl.ProcessValidator;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.utils.IDGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Jacky.gao
 * @since 2013年8月3日
 */
public class DefaultProcessDeployer implements ProcessDeployer{
	private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private ProcessValidator processValidator;
	private CommandService commandService;
	@Override
    public ProcessDefinition deploy(InputStream inputStream) {
		ProcessDefinition process;
		long processId=IDGenerator.getInstance().nextId();
		if(inputStream instanceof ZipInputStream){
			//表示通过zip打包上传的流程模版文件，其中可能包含流程图片文件(png格式)
			ZipInputStream zipInputStream=(ZipInputStream)inputStream;
			process = deployZip(zipInputStream,processId);
		}else{
			//认为是一个以uflo.xml结尾的普通的流程模版文件
			process = deployFile(inputStream,null,processId,false);
		}
		return process;
	}
	
	@Override
	public ProcessDefinition deploy(InputStream inputStream, int version, long processId) {
		return deployFile(inputStream,version,processId,true);
	}

	private ProcessDefinition deployFile(InputStream inputStream,Integer version,long processId,boolean update) {
		ProcessDefinition process;
		try{
			byte[] bytes=IOUtils.toByteArray(inputStream);
			validateProcess(new ByteArrayInputStream(bytes));
			process=ProcessParser.parseProcess(bytes,processId,update);
			assert process != null;
			process.setCreateDate(new Date());
			if(update){
				process.setVersion(version);				
			}
			commandService.executeCommand(new DeployProcessCommand(process,update));
			commandService.executeCommand(new DeployProcessResourceCommand(bytes,process.getName()+PROCESS_EXTENSION_NAME,processId,update));
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			IOUtils.closeQuietly(inputStream);
		}
		return process;
	}
	
	
	private ProcessDefinition deployZip(ZipInputStream zipInputStream,long processId) {
		ProcessDefinition process=null;
		try {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				String entryName = zipEntry.getName();
				if(entryName.endsWith(PROCESS_EXTENSION_NAME)){
					byte[] bytes = IOUtils.toByteArray(zipInputStream);
					validateProcess(new ByteArrayInputStream(bytes));
					process=ProcessParser.parseProcess(bytes,processId,false);
					commandService.executeCommand(new DeployProcessCommand(process,false));
					commandService.executeCommand(new DeployProcessResourceCommand(bytes,entryName,processId,false));
				}else if(entryName.endsWith(PROCESSIMG_EXTENSION_NAME)){
					byte[] bytes = IOUtils.toByteArray(zipInputStream);
					commandService.executeCommand(new DeployProcessResourceCommand(bytes,entryName,processId,false));
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(zipInputStream);
		}
		return process;
	}

	private void validateProcess(InputStream inputStream) {
		StringBuilder errorInfo=new StringBuilder();
        try {
        	Document document = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        	List<String> errors= new ArrayList<>();
        	List<String> nodeNames= new ArrayList<>();
        	Element element=document.getDocumentElement();
        	if(processValidator.support(element)){
        		processValidator.validate(element, errors, nodeNames);
        		if(errors.size()>0){
        			for(int i=0;i<errors.size();i++){
        				errorInfo.append(i + 1).append(".").append(errors.get(i)).append("\r\r");
        			}
        		}
        	}else{
        		errorInfo.append("当前XML文件不是一个合法的UFLO流程模版文件");
        	}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
       String msg=errorInfo.toString();
       if(StringUtils.isNotEmpty(msg)){
    	   throw new ProcessValidateException(msg);
       }
	}
	
	public void setProcessValidator(ProcessValidator processValidator) {
		this.processValidator = processValidator;
	}

	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}
}
