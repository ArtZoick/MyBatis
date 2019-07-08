package top.zoick.mybatis.utils;

import top.zoick.mybatis.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author zoick
 * @date 2019/7/5 22:35
 * 用于创建数据源的工具类
 */
public class DataSourceUtil {
    /**
     * 用于获取一个链接
     * @param cfg
     * @return
     */
    public static Connection getConnection(Configuration cfg) {
        try {
            Class.forName(cfg.getDriver());
            return DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(), cfg.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
