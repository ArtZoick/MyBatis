package top.zoick.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.security.krb5.internal.ktab.KeyTabInputStream;
import top.zoick.dao.IUserDao;
import top.zoick.domain.QueryVo;
import top.zoick.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zoick
 * @date 2019/7/4 21:27
 * mybatis的入门案例
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法执行之前执行
    public void init() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        sqlSession = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
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
        init();
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试根据id查询用户
     */
    @Test
    public void testFindById() throws Exception {
        init();
        //5.执行查询一个
        User user = userDao.findByID(42);
        System.out.println(user);
    }

    /**
     * 测试名称模糊查询用户信息
     */
    @Test
    public void testFindByName() throws Exception {
        init();
        //5.执行模糊查询
        List<User> user = userDao.findByName("%彭%");
//        List<User> user = userDao.findByName("彭");
        System.out.println(user);
    }

    /**
     * 测试使用QueryVo作为查询条件
     */
    @Test
    public void testFindUserByVo() throws Exception {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUserName("%彭%");
        vo.setUser(user);

        List<User> users = userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * 测试findByCondition
     *
     * @throws IOException
     */
    @Test
    public void testFindByCondiion() throws Exception {
        User u = new User();
        u.setUserName("zoick");
//        u.setUserSex("女");
        init();
        //5.使用代理对象执行方法
        List<User> users = userDao.findByCondition(u);
        for (User user : users) {
            System.out.println(user);
        }


    }

    /**
     * 测试foreach标签的使用
     *
     * @throws IOException
     */
    @Test
    public void testFindInIds () throws Exception {
        QueryVo vo = new QueryVo();
        List<Integer> list = new ArrayList<Integer>();
        list.add(41);
        list.add(42);
        list.add(45);
        vo.setIds(list);
        init();
        //5.使用代理对象执行方法
        List<User> users = userDao.findUserInIds(vo);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
