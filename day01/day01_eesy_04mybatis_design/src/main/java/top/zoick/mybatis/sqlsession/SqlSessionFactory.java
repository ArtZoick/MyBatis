package top.zoick.mybatis.sqlsession;

/**
 * @author zoick
 * @date 2019/7/5 16:14
 */
public interface SqlSessionFactory {

    /**
     * 用于打开一个新的SqlSession
     */
    SqlSession openSession();
}
