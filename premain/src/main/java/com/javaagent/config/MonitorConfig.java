package com.javaagent.config;

/**
 * Created by zhongjing on 2017/11/08.
 */
public interface MonitorConfig {
    /**
     * 设置配置文件
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringValue(String key, String defaultValue);

    /**
     * 获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBooleanValue(String key, boolean defaultValue);

    /**
     * 获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public Integer getIntegerValue(String key, Integer defaultValue);
}
