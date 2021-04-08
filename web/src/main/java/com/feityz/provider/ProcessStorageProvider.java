package com.feityz.provider;

import com.bstek.uflo.console.provider.ProcessFile;
import com.bstek.uflo.console.provider.ProcessProvider;
import com.feityz.system.dao.ProcessModelMapper;
import com.feityz.system.entity.ProcessModel;
import com.feityz.system.service.IProcessModelService;
import lombok.var;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

/**
 * 自定义存储位置
 */
//@Component
public class ProcessStorageProvider implements ProcessProvider {public String prefix = "file:";

    @Autowired
    private IProcessModelService processModelService;
    private ApplicationContext applicationContext;
    @Value("${fileStoreDir}")
    private String fileStoreDir;
    private boolean disabled;
    public ProcessStorageProvider() {
    }

    @Override
    public InputStream loadProcess(String file) {
        if (file.startsWith(this.prefix)) {
            file = file.substring(this.prefix.length(), file.length());
        }

        String fullPath = this.fileStoreDir + "/" + file;

        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException var4) {
            throw new RuntimeException(var4);
        }
    }

    @Override
    public void saveProcess(String file, String content) {
        if (file.startsWith(this.prefix)) {
            file = file.substring(this.prefix.length(), file.length());
        }
        File f = new File(this.fileStoreDir);
        if(!f.exists()){
            f.mkdirs(); //创建目录
        }

        String fullPath = this.fileStoreDir + "/" + file;
        FileOutputStream outStream = null;

        try {
            ProcessModel model = new ProcessModel();
            var models = processModelService.lambdaQuery().eq(ProcessModel::getFileName,file).list();
            if(models.size()>0){
                //修改
                model = models.get(0);
                processModelService.updateById(model);
            }else {
                //新增
                model.setFileName(file);
                model.setProcessKey(getProcessKey(content));
                model.setProcessName(getProcessName(content));
                model.setProcessType("");
                processModelService.save(model);
            }
            outStream = new FileOutputStream(new File(fullPath));
            IOUtils.write(content, outStream, "utf-8");
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

    }

    @Override
    public void deleteProcess(String file) {
        if (file.startsWith(this.prefix)) {
            file = file.substring(this.prefix.length(), file.length());
        }

        String fullPath = this.fileStoreDir + "/" + file;
        File f = new File(fullPath);
        if (f.exists()) {
            f.delete();
        }

    }

    @Override
    public List<ProcessFile> loadAllProcesses() {
        File file = new File(this.fileStoreDir);
        List<ProcessFile> list = new ArrayList();
        File[] var3 = file.listFiles();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            File f = var3[var5];
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(f.lastModified());
            list.add(new ProcessFile(f.getName(), calendar.getTime()));
        }

        Collections.sort(list, new Comparator<ProcessFile>() {
            @Override
            public int compare(ProcessFile f1, ProcessFile f2) {
                return f2.getUpdateDate().compareTo(f1.getUpdateDate());
            }
        });
        return list;
    }

    public void afterPropertiesSet() throws Exception {
        File file = new File(this.fileStoreDir);
        if (!file.exists()) {
            WebApplicationContext context = (WebApplicationContext)this.applicationContext;
            ServletContext servletContext = context.getServletContext();
            String basePath = servletContext.getRealPath("/");
            this.fileStoreDir = basePath + this.fileStoreDir;
            file = new File(this.fileStoreDir);
            if (!file.exists()) {
                file.mkdirs();
            }

        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setFileStoreDir(String fileStoreDir) {
        this.fileStoreDir = fileStoreDir;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean support(String fileName) {
        return fileName.startsWith(this.prefix);
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getName() {
        return "系统存储路径";
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
