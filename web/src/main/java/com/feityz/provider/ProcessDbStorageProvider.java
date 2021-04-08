package com.feityz.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bstek.uflo.console.provider.ProcessFile;
import com.bstek.uflo.console.provider.ProcessProvider;
import com.feityz.system.entity.ProcessModel;
import com.feityz.system.service.IProcessModelService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDbStorageProvider implements ProcessProvider {
    public String prefix="file:";
    private boolean disabled = false;
    @Autowired
    private IProcessModelService processModelService;
    @Override
    public InputStream loadProcess(String fileName) {
        if (fileName.startsWith(this.prefix)) {
            fileName = fileName.substring(this.prefix.length(), fileName.length());
        }

        ProcessModel file = processModelService.lambdaQuery()
                .eq(ProcessModel::getFileName,fileName).one();

        return new ByteArrayInputStream(file.getContent().getBytes(Charset.forName("utf-8")));
    }
    @Override
    public List<ProcessFile> loadAllProcesses() {
        List<ProcessModel> dbFileList = processModelService.lambdaQuery().list();
        List<ProcessFile> fileList = new ArrayList<ProcessFile>();
        for(ProcessModel df : dbFileList){
            ProcessFile f = new ProcessFile(df.getFileName(), df.getCreateTime());
            fileList.add(f);
        }
        return fileList;

    }
    @Override
    public void saveProcess(String fileName, String content) throws DocumentException {

        if (fileName.startsWith(this.prefix)) {
            fileName = fileName.substring(this.prefix.length(), fileName.length());
        }
        ProcessModel df = processModelService.lambdaQuery()
                .eq(ProcessModel::getFileName,fileName).one();
        if(df == null) {
            df = new ProcessModel();
            df.setFileName(getRealName(fileName));
            df.setContent(content);

            df.setProcessName(getProcessName(content));
            df.setProcessKey(getProcessKey(content));
            processModelService.saveOrUpdate(df);
        } else {
            df.setContent(content);
            df.setProcessName(getProcessName(content));
            df.setProcessKey(getProcessKey(content));
            processModelService.saveOrUpdate(df);
        }

    }
    @Override
    public void deleteProcess(String fileName) {
        if (fileName.startsWith(this.prefix)) {
            fileName = fileName.substring(this.prefix.length(), fileName.length());
        }
        QueryWrapper<ProcessModel> queryWrapper = new QueryWrapper();
        queryWrapper.eq("FILE_NAME_",fileName);
        processModelService.remove(queryWrapper);
    }
    @Override
    public String getName() {
        return "dbstore";
    }
    @Override
    public String getPrefix() {
        return prefix;
    }
    @Override
    public boolean support(String fileName) {
        return fileName.startsWith(prefix);
    }
    @Override
    public boolean isDisabled() {
        return disabled;
    }
    private String getRealName(String name){
        if(name.startsWith(getPrefix())){
            return name.substring(name.indexOf(getPrefix())+3);
        }
        return name;
    }

    private String getProcessName(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml); // 将字符串转为xml

        Element root = doc.getRootElement(); // 获取根节点
        System.out.println(root.getName());
        return root.attributes().get(0).getValue();
    }

    private String getProcessKey(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml); // 将字符串转为xml

        Element root = doc.getRootElement(); // 获取根节点
        System.out.println(root.getName());
        return root.attributes().get(1).getValue();
    }
}
