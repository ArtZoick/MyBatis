package top.zoick.mybatis.sqlsession.defauls;

import top.zoick.mybatis.cfg.Configuration;
import top.zoick.mybatis.sqlsession.SqlSession;
import top.zoick.mybatis.sqlsession.SqlSessionFactory;

/**
 * @author zoick
 * @date 2019/7/5 22:00
 * SqlSessionFactory接口的实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration cfg;

    public DefaultSqlSessionFactory(Configuration cfg) {
        this.cfg = cfg;
    }

    /**
     * 用于创建一个新的操作数据库对象
     * @return
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(cfg);
    }
}
