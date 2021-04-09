package com.asideal;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.asideal.common.BaseEntity;
import com.asideal.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * @author zhangjie
 * @date 2021-04-08
 */
public class MysqlGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    private static String url = "jdbc:mysql://47.92.145.11:3306/uflo?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

    private static String userName = "root";

    private static String password = "123456";

    private static String tableName = "uflo_his_task";

    private static String fileRoot = "D:/dev";

    private static String[] prefix = {"biz_", "sys_", "base_","bas_","uflo_"};


    public static void main(String[] args) {

        FileUtil.deleteDirectory(fileRoot);
        String packName = tableName.split("_")[1];

        int result = 0;
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                // 输出目录
                new GlobalConfig().setOutputDir("D:\\dev")
                        // 是否覆盖文件
                        .setFileOverride(true)
                        // 开启 activeRecord 模式
                        .setActiveRecord(true)
                        // XML 二级缓存
                        .setEnableCache(false)
                        // XML ResultMap
                        .setBaseResultMap(true)
                        // XML columList
                        .setBaseColumnList(true)
                        // .setKotlin(true) 是否生成 kotlin 代码
                        .setAuthor("Line")
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                // .setEntityName("%sBean")
                // .setMapperName("%sDao")
                // .setXmlName("%sDao")
                // .setServiceName("I%sService")
                // .setServiceImplName("%sService")
                // .setControllerName("%sAction")
        ).setDataSource(
                // 数据源配置
                // 数据库类型
                new DataSourceConfig().setDbType(DbType.MYSQL)
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                if (fieldType.toLowerCase().contains("tinyint")) {
                                    return DbColumnType.BOOLEAN;
                                } else if (fieldType.toLowerCase().contains("date")) {
                                    return DbColumnType.DATE;
                                } else if (fieldType.toLowerCase().contains("decimal")) {
                                    return DbColumnType.DOUBLE;
                                }
                                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                            }
                        }).setDriverName("com.mysql.cj.jdbc.Driver").setUsername(userName)
                        .setPassword(password)
                        .setUrl(url))
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // 全局大写命名
                                // .setCapitalMode(true)
                                //全局下划线命名
                                // .setDbColumnUnderline(true)
                                // 此处可以修改为您的表前缀
                                .setTablePrefix(prefix)
                                // 表名生成策略
                                .setNaming(NamingStrategy.underline_to_camel)
                                // 需要生成的表
                                .setInclude(tableName)
                                // 排除生成的表
                                // .setExclude(new String[]{"test"})
                                // 自定义实体父类
                                .setSuperEntityClass(BaseEntity.class)
                                // 自定义实体，公共字段
                                .setSuperEntityColumns("id", "flag", "insert_time", "insert_user",
                                        "update_time", "update_user")
                                .setTableFillList(tableFillList)
                                // 自定义 mapper 父类
                                // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                                // 自定义 service 父类
                                // .setSuperServiceClass("com.baomidou.demo.TestService")
                                // 自定义 service 实现类父类
                                // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                                // 自定义 controller 父类
                                // .setSuperControllerClass("com.baomidou.demo.TestController")
                                // 【实体】是否生成字段常量（默认 false）
                                // public static final String ID = "test_id";
                                .setEntityColumnConstant(true)
                                // 【实体】是否为构建者模型（默认 false）
                                // public User setName(String name) {this.name = name; return this;}
                                // .setEntityBuilderModel(true)
                                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                                .setEntityLombokModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        // .setEntityBooleanColumnRemoveIsPrefix(true)
                        // .setRestControllerStyle(true)
                        // .setControllerMappingHyphenStyle(true)
                ).setPackageInfo(
                        // 包配置
                        new PackageConfig()
                                .setModuleName(packName)
                                .setParent("com.feityz.system")
                                .setEntity("entity")
                                .setMapper("dao")
                                // 这里是控制器包名，默认 web
                                .setController("controller")
                ).setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>(16);
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }.setFileOutConfigList(getCustom()))
                .setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml(null)
                                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                                .setController("/templates/generator/controller.java")
                                .setEntity("/templates/generator/bean.java")
                                .setMapper("/templates/generator/mapper.java")
                                //.setXml("/templates/generator/mapper.xml")
                                .setService("/templates/generator/service.java")
                                .setServiceImpl("/templates/generator/serviceImpl.java"));
        // 执行生成
        if (1 == result) {
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        }
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

    private static List<FileOutConfig> getCustom() {
        List<FileOutConfig> fileOutList = new ArrayList<>();
        fileOutList.add(new FileOutConfig("/templates/generator/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return fileRoot + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        fileOutList.add(new FileOutConfig("/templates/generator/page.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return fileRoot + "/" + tableInfo.getEntityPath() + "/index.html";
            }
        });
        fileOutList.add(new FileOutConfig("/templates/generator/apiController.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return fileRoot + "/apiController/" + tableInfo.getEntityName() + "Controller.java";
            }
        });
        /*
         * fileOutList.add(new FileOutConfig("/templates/generator/page.js.vm") {
         *
         * @Override public String outputFile(TableInfo tableInfo) { return "d:/dev/" +
         * tableInfo.getEntityPath() + "/" + tableInfo.getEntityPath() + ".js"; } });
         */
        return fileOutList;
    }

}
