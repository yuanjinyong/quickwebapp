/**
 * 
 */
package com.quickwebapp.tools.mybatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author JohnYuan
 *
 */
public class MyBatisGeneratorApplication {
    private static Log logger = LogFactory.getLog(MyBatisGeneratorApplication.class);

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        String configFilesDir = resolver.getResource("classpath:mybatis/generator").getURL().toString();
        Resource[] resources = resolver.getResources("classpath:mybatis/generator/**/*.generator.config.xml");

        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        for (Resource resource : resources) {
            try {
                String resourceUrl = resource.getURL().toString();
                String configFileName = resource.getFilename();
                String defaultPackage = resourceUrl
                        .substring(configFilesDir.length() + 1, resourceUrl.length() - configFileName.length() - 1)
                        .replaceAll("/", ".");
                String defaultClassName = configFileName.substring(0,
                        configFileName.length() - ".generator.config.xml".length());
                System.setProperty("mybatis.generator.defaultPackage", defaultPackage);
                System.setProperty("mybatis.generator.javaModelPackage", defaultPackage + ".entity");
                System.setProperty("mybatis.generator.javaClientPackage", defaultPackage + ".mapper");
                System.setProperty("mybatis.generator.sqlMapPackage", defaultPackage + ".xml");
                System.setProperty("mybatis.generator.defaultClassName", defaultClassName);
                logger.debug("生成" + resource.getDescription());
                Configuration config = cp.parseConfiguration(resource.getInputStream());
                MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
                myBatisGenerator.generate(null);
            } catch (Exception e) {
                logger.error("生成" + resource.getURI() + "失败！", e);
            }
        }
    }

}
