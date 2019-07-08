package top.zoick.mybatis.sqlsession;

import top.zoick.mybatis.cfg.Configuration;
import top.zoick.mybatis.sqlsession.defauls.DefaultSqlSessionFactory;
import top.zoick.mybatis.utils.XMLConfigBuilder;

import java.io.InputStream;

/**
 * @author zoick
 * @date 2019/7/5 16:12
 * 用于创建一个SqlsessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据参数的字节输入流来构建一个sqlSessionFactory工厂
     * @param config
     * @return
     */
    public SqlSessionFactory build(InputStream config) {
        Configuration cfg = XMLConfigBuilder.loadConfiguration(config);
        return new DefaultSqlSessionFactory(cfg);
    }
}
