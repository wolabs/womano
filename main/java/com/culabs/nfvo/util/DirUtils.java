package com.culabs.nfvo.util;

import java.io.File;

/**
 * 
 * @ClassName: DirUtils 
 * @Description: TODO
 * @author 
 * @date 2015年4月23日 下午3:51:13 
 * @version 1.0
 */
public class DirUtils
{
    // private static Logger LOGGER = LoggerFactory.getLogger(SYSDirs.class);
    public final static DirUtils SYS_DIRS = new DirUtils();

    private final static String ROOT_DIR = System.getProperty("user.dir");

    public final static String TEMPLATE_NS = "ns";

    public final static String TEMPLATE_VNF = "vnf";
    
    public final static String TEMPLATE_TEMP = "temp";

    private static String webappdir = "";

    private static String configdir = "";

    private static String logdir = "";

    private static String templatedir = "";

    private DirUtils()
    {
    }

    public static DirUtils getInstance()
    {
        return SYS_DIRS;
    }

    public String getRootDir()
    {
        return ROOT_DIR;
    }

    public String getWebAppDir()
    {
        if (NFVOUtils.isEmpty(webappdir))
        {
            if (isDeployEnv())
            {
                webappdir = webappdir.concat(ROOT_DIR).concat("/../web/");
            }
            else
            {
                webappdir = webappdir.concat(ROOT_DIR).concat(
                        "/src/main/webapp/");
            }
            // LOGGER.info("webappdir= {}", webappdir);
        }
        return webappdir;
    }

    public String getConfigDir()
    {
        if (NFVOUtils.isEmpty(configdir))
        {
            if (isDeployEnv())
            {
                configdir = configdir.concat(ROOT_DIR).concat("/../config/");
            }
            else
            {
                configdir = configdir.concat(ROOT_DIR).concat(
                        "/src/main/resources/config/");
            }
            // LOGGER.info("configdir= {}", configdir);
        }
        return configdir;
    }

    public String getTemplDir(String tmplType)
    {
        if (NFVOUtils.isEmpty(templatedir))
        {
            if (isDeployEnv())
            {
                templatedir = templatedir.concat(ROOT_DIR).concat(
                        "/../template/");
            }
            else
            {
                templatedir = templatedir.concat(ROOT_DIR).concat(
                        "/src/main/resources/template/");
            }
            // LOGGER.info("configdir= {}", configdir);
        }
        return new StringBuffer(templatedir).append(tmplType).append("/")
                .toString();
    }

    public String getLogDir()
    {
        if (NFVOUtils.isEmpty(logdir))
        {
            if (isDeployEnv())
            {
                logdir = logdir.concat(ROOT_DIR).concat("/../logs/");
            }
            else
            {
                logdir = logdir.concat(ROOT_DIR).concat(
                        "/src/main/resources/logs");
            }
            // LOGGER.info("logdir= {}", logdir);
        }
        return logdir;
    }

    private boolean isDeployEnv()
    {
        return (ROOT_DIR + "$").contains(File.separator + "bin$");
    }
}
