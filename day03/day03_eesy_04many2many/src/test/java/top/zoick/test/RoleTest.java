package top.zoick.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import top.zoick.dao.IRoleDao;
import top.zoick.dao.IUserDao;
import top.zoick.domain.Role;
import top.zoick.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zoick
 * @date 2019/7/11 15:44
 */
public class RoleTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IRoleDao roleDao;

    @Before//用于在测试方法执行之前执行
    public void init() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        sqlSession = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        roleDao = sqlSession.getMapper(IRoleDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destory() throws IOException {

        //提交事务
        sqlSession.commit();

        //6.释放资源
        sqlSession.close();
        in.close();

    }


    /**
     * 测试查询所有
     *
     * @throws IOException
     */
    @Test
    public void testFindALl() throws Exception {
       List<Role> roles = roleDao.findAll();
       for(Role role : roles){
           System.out.println("------每个角色的信息------");
           System.out.println(role);
           System.out.println(role.getUsers());
       }
    }
}
