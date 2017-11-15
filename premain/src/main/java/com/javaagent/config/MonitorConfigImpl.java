package com.javaagent.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhongjing on 2017/11/08.
 */
public class MonitorConfigImpl implements MonitorConfig {

    private String MONITOR_CONF = "monitor.conf";
    private Properties prop = null;

    public MonitorConfigImpl() throws IOException {
        prop = System.getProperties();
        String monitorConf = prop.getProperty(MONITOR_CONF);
        File monitorFile = new File(monitorConf);
        InputStream inputStream = new FileInputStream(monitorFile);
        prop.load(inputStream);
    }

    /* (non-Javadoc) 
     * @see monitor.agent.MonitorConfig#getStringValue(java.lang.String, java.lang.String) 
     */
    @Override
    public String getStringValue(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    /* (non-Javadoc) 
     * @see monitor.agent.MonitorConfig#getBooleanValue(java.lang.String, boolean) 
     */
    @Override
    public boolean getBooleanValue(String key, boolean defaultValue) {
        String value = prop.getProperty(key, null);

        return (null != value) ? Boolean.valueOf(value) : defaultValue;
    }

    /* (non-Javadoc) 
     * @see monitor.agent.MonitorConfig#getIntegerValue(java.lang.String, java.lang.Integer) 
     */
    @Override
    public Integer getIntegerValue(String key, Integer defaultValue) {
        String value = prop.getProperty(key, null);
        return (null != value) ? Integer.valueOf(value) : defaultValue;
    }
}
