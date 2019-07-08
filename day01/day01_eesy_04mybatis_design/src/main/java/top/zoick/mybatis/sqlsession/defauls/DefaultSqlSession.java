package top.zoick.mybatis.sqlsession.defauls;

import top.zoick.mybatis.cfg.Configuration;
import top.zoick.mybatis.sqlsession.SqlSession;
import top.zoick.mybatis.sqlsession.proxy.MapperProxy;
import top.zoick.mybatis.utils.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zoick
 * @date 2019/7/5 22:07
 * SqlSession接口的实现类
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration cfg;
    private Connection connection;

    public DefaultSqlSession(Configuration cfg) {
        this.cfg = cfg;
        connection = DataSourceUtil.getConnection(cfg);
    }

    /**
     * 根据参数创建一个代理对象
     *
     * @param daoInterfaceClass dao的接口字节码
     * @return
     */
    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {

        return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(), new Class[]{daoInterfaceClass},
                new MapperProxy(cfg.getMappers(), connection));


    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
