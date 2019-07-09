package top.zoick.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import top.zoick.dao.IUserDao;
import top.zoick.domain.QueryVo;
import top.zoick.domain.User;

import java.io.IOException;
import java.io.InputStream;
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
     * 测试保存操作
     */
    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setUserName("modify user");
        user.setUserAddress("天津");
        user.setUserBirthday(new Date());
        user.setUserSex("男");

        System.out.println("保存操作之前：" + user);
        init();
        //5.使用代理对象执行方法
        userDao.saveUser(user);

        System.out.println("保存操作之后：" + user);
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setUserId(52);
        user.setUserName("zzz1");
        user.setUserAddress("天津1");
        user.setUserBirthday(new Date());
        user.setUserSex("男");

        init();
        //5.使用代理对象执行方法
        userDao.updateUser(user);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() throws Exception {

        init();
        //5.执行删除方法
        userDao.deleteUser(51);
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
     * 测试查询用户条数
     */
    @Test
    public void testFindTotal() throws Exception {
        init();
        //执行查询总记录条数
        Integer number = userDao.findTotal();
        System.out.println("总记录条数为：" + number);
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


}
