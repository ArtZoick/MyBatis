# MyBatis_1	简介

## 1、什么是框架？
它是我们软件开发中的一套解决方案，不同的框架解决的是不同的问题
使用框架的好处：
框架封装了很多的细节，使开发者可以使用极简的方式实现功能。大大提高开发效率。

## 2、三层架构
**表现层：**  
是用于展示数据的  
**业务层：**  
是处理业务需求  
**持久层：**  
是和数据库交互的  

## 3、持久层技术解决方案

**JDBC技术：**  
Connection  
PreparedStatement  
ResultSet  
**Spring的JdbcTemplate：**  
Spring中对jdbc的简单封装  
**Apache的DBUtils：**  
它和Spring的JdbcTemplate很像，也是对Jdbc的简单封装  
以上这些都不是框架  
JDBC是规范  

Spring的JdbcTemplate和Apache的DBUtils都只是工具类  

## 4、mybatis的概述

mybatis是一个持久层框架，用java编写的。  
它封装了jdbc操作的很多细节，使开发者只需要关注sql语句本身，而无需关注注册驱动，创建连接等繁杂过程。  
它使用了ORM思想实现了结果集的封装。  

**ORM：**  
Object Relational Mappging 对象关系映射  
简单的说：就是把数据库表和实体类及实体类的属性对应起来，让我们可以操作实体类就实现操作数据库表。  
user					User  
id						userId
user_name	userName

我们需要做到  
*实体类中的属性和数据库表的字段名称保持一致。*

## 5、mybatis的入门

### 表的创建与数据的插入

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/0_1.png)

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/0_2.png)

### mybatis环境搭建demo的基本架构

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/1.png)

**第一步：创建maven工程并导入坐标**  

**第二步：创建实体类和dao的接口**  

**第三步：创建Mybatis的主配置文件**  

*SqlMapConifg.xml*    

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- mybatis主配置文件-->
<configuration>
    <!-- 配置环境-->
    <environments default="mysql">
        <!-- 配置mysql的环境-->
        <environment id="mysql">
            <!-- 配置事务的类型-->
            <transactionManager type="jdbc"></transactionManager>
            <!-- 配置数据源（连接池）-->
            <dataSource type="POOLED">
                <!-- 配置链接数据库的四个基本信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/eesy_mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件-->
    <mappers>
        <mapper resource="top/zoick/dao/IUserDao.xml"/>
    </mappers>
</configuration>
```

**第四步：创建映射配置文件**  

*IUserDao.xml*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="top.zoick.dao.IUserDao">
    <!--配置查询所有  其中id不能乱写必须是dao接口中的方法  resultType写的是实体类的全路径-->
    <select id="findAll" resultType="top.zoick.domain.User">
        select * from user
    </select>
</mapper>
```

### 环境搭建的注意事项：  

**第一个：**创建IUserDao.xml 和 IUserDao.java时名称是为了和我们之前的知识保持一致。  
在Mybatis中它把持久层的操作接口名称和映射文件也叫做：Mapper  
所以：IUserDao 和 IUserMapper是一样的  
**第二个：**在idea中创建目录的时候，它和包是不一样的  
包*（Package）*在创建时：top.zoick.dao它是三级结构  
目录*（Directory）*在创建时：top.zoick.dao是一级目录  
**第三个：**mybatis的映射配置文件位置必须和dao接口的包结构相同  
**第四个：**映射配置文件的mapper标签namespace属性的取值必须是dao接口的全限定类名  
**第五个：**映射配置文件的操作配置（select），id属性的取值必须是dao接口的方法名  

当我们遵从了第三，四，五点之后，我们在开发中就无须再写dao的实现类。  

### mybatis的入门案例  

**第一步：**读取配置文件  
**第二步：**创建SqlSessionFactory工厂  
**第三步：**创建SqlSession  
**第四步：**创建Dao接口的代理对象  
**第五步：**执行dao中的方法  
**第六步：**释放资源  

**注意事项：**  
不要忘记在映射配置中告知mybatis要封装到哪个实体类中  
配置的方式：指定实体类的全限定类名      

**mybatis基于注解的入门案例：**  
把IUserDao.xml移除，在dao接口的方法上使用@Select注解，并且指定SQL语句  
同时需要在SqlMapConfig.xml中的mapper配置时，使用class属性指定dao接口的全限定类名。  
**明确：**  
我们在实际开发中，都是越简便越好，所以都是采用不写dao实现类的方式。  
不管使用XML还是注解配置。      

但是Mybatis它是支持写dao实现类的。   

## 6、自定义Mybatis的分析__执行查询所有的分析

mybatis在使用代理dao的方式实现增删改查时做什么事呢？  
只有两件事：  
第一：创建代理对象  
第二：在代理对象中调用selectList  

自定义mybatis能通过入门案例看到类  
class　　　　Resources　　使用类加载器读取配置文件的类   
class　　　　SqlSessionFactoryBuilder　　用于创建一个SqlsessionFactory对象  
interface　　SqlSessionFactory　　用于打开一个新的SqlSession  
interface　　SqlSession　　自定义MYbatis中和数据库交互的核心类

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/2.png)  

自定义mybatis开发流程图:

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/2_2.png)

Project结构：

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/2_1.png)

1.读取配置文件用io包里的Resources
2.读出所需要的信息交给SqlSessionFactoryBuilder构建者
3.构建者使用工具类XMLConfigBuilder构建出DefaultSqlsessionFactory工厂对象，xml读取的信息保存在Configuration里
4.工厂里的openSession提供了Sqlsession方法
5.再在SqlSession里实现创建代理对象和查询所有的功能

测试类  

```java
package top.zoick.test;


import top.zoick.dao.IUserDao;
import top.zoick.domain.User;
import top.zoick.mybatis.io.Resources;
import top.zoick.mybatis.sqlsession.SqlSession;
import top.zoick.mybatis.sqlsession.SqlSessionFactory;
import top.zoick.mybatis.sqlsession.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zoick
 * @date 2019/7/4 21:27
 * mybatis的入门案例
 */
public class MybatisTest {
    public static void main(String[] args) throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user: users) {
            System.out.println(user);
        }
        //6.释放资源
        session.close();
        in.close();
    }
}
```

## 项目源码：[点鸡这里](https://github.com/ArtZoick/MyBatis)

--------------------



# MyBatis_2	基本的CRUD

1、回顾MyBatis的自定义再分析和环境搭建+完善基于注解的MyBatis  
2、MyBatis的CRUD（基于代理dao的方式）  
3、MyBatis中的参数深入及结果集的深入  
4、MyBatis中基于传统dao的方式（编写dao的实现类）---了解的内容  
5、MyBatis中的配置(主配置文件：SqlMapConfig.xml)  
　　　　　properties标签  
　　　　　typeAliases标签  
　　　　　mappers标签  

**OGNL表达式:**  
Object Graphic Navigation Language  
对象 　　图　　　导航 　　　语言  
它是通过对象的取值方法来获取数据。·在写法上把get给省略了   
比如:我们获取用户的名称   
　　　　类中的写法:user.getUsername,  
　　　　OGNL表达式写法:user.username   
mybatis中为什么能直接写username,而不用user.呢:  
 　　　　因为在parameterType中已经提供了属性所属的类, 所以此时不需要写对象名



## 完整接口类代码

```java
/**
 * @author zoick
 * @date 2019/7/4 17:34
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有操作
     * @return
     */
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据删除用户
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    User findByID(Integer userId);

    /**
     * 根据名称模糊查询用户信息
     * @param username
     * @return
     */
    List<User> findByName(String username);


    /**
     * 查询总用户数
     * @return
     */
    Integer findTotal();

    /**
     * 根据QueryVo中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);


}
```

## 完整mapper代码

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="top.zoick.dao.IUserDao">

    <!--配置查询结果的列名和实体类的属性名的对应关系-->
    <resultMap id="userMap" type="top.zoick.domain.User">
        <!--主键字段的对应-->
        <id property="userId" column="id"></id>
        <!--非主键字段的对应-->
        <result property="userName" column="username"></result>
        <result property="userAddress" column="address"></result>
        <result property="userSex" column="sex"></result>
        <result property="userBirthday" column="birthday"></result>
    </resultMap>
    

    <!--配置查询所有  其中id不能乱写必须是dao接口中的方法  resultType写的是实体类的全路径-->
    <select id="findAll" resultMap="userMap">
        <!--实体类中的属性名和数据库表中元素名字匹配不上时，使用别名即可匹配。id为数据库中表的列名，userId为实体类中属性名-->
        <!--select id as userId,username as userName,address as userAddress ,sex as userSex, birthday as userBirthday from user-->
        select * from user
</select>

    <!--保存用户-->
    <insert id="saveUser" parameterType="top.zoick.domain.User">
        <!--配置插入操作后，获取插入数据的id-->
            <!--keyProperty对应实体类属性名:id，keyColumn对应于数据库:id resultType为数据类型，order为在这条insert sql语句执行前还是执行后做-->
        <selectKey keyProperty="userId" keyColumn="id" resultType="int" order="AFTER">
            select last_insert_id();
        </selectKey>
        insert into user (username,address,sex,birthday) values (#{userName},#{userAddress},#{userSex},#{userBirthday})
    </insert>

    <!--更新用户-->
    <update id="updateUser" parameterType="usER">
        update user set username=#{userName},address=#{userAddress},sex=#{userSex},birthday=#{userBirthday} where id=#{userId}
    </update>

    <!--删除用户-->
    <!--当参数值为基本类型或者基本类型包装类时，SQL语句中的占位符可以随意写-->
    <delete id="deleteUser" parameterType="Integer">
        delete from user where id = #{anyzhanweifu}
    </delete>

    <!--根据id查询用户-->
    <select id="findByID" parameterType="INT" resultMap="userMap">
        select * from user where id = #{uuuuuuid}
    </select>

    <!--根据名称模糊查询-->
    <select id="findByName" parameterType="String" resultMap="userMap">
        select * from user where username like #{namenamenamename}
        <!--select * from user where username like '%${value}%'-->
    </select>

    <!--查询记录的总用户条数-->
    <select id="findTotal" resultType="Integer">
        select count(id) from user
    </select>

    <!--根据QueryVo的条件查询用户-->
    <select id="findUserByVo" parameterType="top.zoick.domain.QueryVo" resultMap="userMap">
        select * from user where username like #{user.username}
    </select>

</mapper>
```

## 完整测试类代码

```java
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
        user.setUserId(51);
        user.setUserName("mybatis updateUser");
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
```

## 一些注意点：

### 1、数据库表的元素名与实体类的属性名不对应的解决：

1.起别名：    

```xml
 <!--配置查询所有  其中id不能乱写必须是dao接口中的方法  resultType写的是实体类的全路径-->
    <select id="findAll" resultType="top.zoick.domain.User">
        <!--实体类中的属性名和数据库表中元素名字匹配不上时，使用别名即可匹配。id为数据库中表的列名，userId为实体类中属性名-->
        select id as userId,username as userName,address as userAddress ,sex as userSex, birthday as userBirthday from user
    </select>
```

2.配置查询结果的列名和实体类的属性名的对应关系：

```xml
 <!--配置查询结果的列名和实体类的属性名的对应关系-->
    <resultMap id="userMap" type="top.zoick.domain.User">
        <!--主键字段的对应-->
        <id property="userId" column="id"></id>
        <!--非主键字段的对应-->
        <result property="userName" column="username"></result>
        <result property="userAddress" column="address"></result>
        <result property="userSex" column="sex"></result>
        <result property="userBirthday" column="bithday"></result>
    </resultMap>

再在查询的sql语句的xml中加入配置
  <select id="findAll" resultMap="userMap">
        select * from user
  </select>
```



### 2、配置xml中的属性标签

### (1)使用properties配置数据库连接欸信息     

可以在标签内部配置数据库连接信息，也可以通过外部文件来配置数据库连接信息。

**第一种url属性(不常用)**

 URL属性：  
　　URL:Uniform Resource Locator　统一资源定位符 可以唯一标志一个资源的位置  
　　写法必须是  
　　　　http://localhost:8080/mybatisserver/demo1Servlet  
　　　　协议　　主机　　端口　URI  
　　URI:Uniform Resource Identifier   统一资源标识符 是在应用中可以可以唯一标志一个资源的位置  
　　URL>URI（精准性）  

```xml
<properties url="file:///C:/Users/zoick/OneDrive/Tomorrow/%E6%A1%86%E6%9E%B6%E5%AD%A6%E4%B9%A0/MyBatis/day02/day02_eesy_02mybatisCRUD/src/main/resources/jdbcConfig.properties">

</properties>
```

**第二种resource属性（常用）**  
用于指定配置文件的位置，是按照类路径来写的，必须存在于类路径下  

```xml
<properties resource="jdbcConfig.properties">

</properties>
```

### (2)使用typeAliases配置别名

```xml
<!--使用typeAliases配置别名，他只能配置domain中类的别名-->
    <typeAliases>
        <!--typeAlias用于配置别名，type属性指定的是实体类中的全限定类名。alias属性指定别名，当指定了别名后不在区分大小写-->
        <typeAlias type="top.zoick.domain.User" alias="user"></typeAlias>
    </typeAliases>
```

### (3)使用package配置别名

```xml
 <typeAliases>
        <!--用于指定要配置别名的包，当指定后，该包下的实体类都会注册别名，并且类名就是别名，不再区分大小写-->
        <package name="top.zoick.domain"/>
    </typeAliases>
```

其中，配置映射文件位置的\<mappers>中也有package这个标签

```xml
   <mappers>
<!--        <mapper resource="top/zoick/dao/IUserDao.xml"/>-->
        <!--package标签是用于指定dao接口所在的包，当指定了之后就不需要再写mapepr以及resource或者class了-->
        <package name="top.zoick.dao"/>
    </mappers>

```

配置文件：

*SqlMapConfig.xml*  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- mybatis主配置文件-->
<configuration>
    <!--配置properties
        可以在标签内部配置数据库连接信息 也可以通过外部文件来配置数据库连接信息
        resource 属性：（常用）
                用于指定配置文件的位置，是按照类路径来写的，必须存在于类路径下
        URL属性：
            URL:Uniform Resource Locator    统一资源定位符 可以唯一标志一个资源的位置
            写法必须是
                http://localhost:8080/mybatisserver/demo1Servlet
                协议      主机    端口     URI
            URI:Uniform Resource Identifier 统一资源标识符 是在应用中可以可以唯一标志一个资源的位置
            URL>URI（精准性）
    -->
    <properties url="file:///C:/Users/zoick/OneDrive/Tomorrow/%E6%A1%86%E6%9E%B6%E5%AD%A6%E4%B9%A0/MyBatis/day02/day02_eesy_02mybatisCRUD/src/main/resources/jdbcConfig.properties">
<!--        <property name="driver" value="com.mysql.cj.jdbc.Driver"/>-->
<!--        <property name="url" value="jdbc:mysql://localhost:3306/eesy_mybatis"/>-->
<!--        <property name="username" value="root"/>-->
<!--        <property name="password" value="root"/>-->
    </properties>
    
    <!--使用typeAliases配置别名，他只能配置domain中类的别名-->
    <typeAliases>
        <!--typeAlias用于配置别名，type属性指定的是实体类中的全限定类名。alias属性指定别名，当指定了别名后不在区分大小写-->
<!--    <typeAlias type="top.zoick.domain.User" alias="user"></typeAlias>-->

        <!--用于指定要配置别名的包，当指定后，该包下的实体类都会注册别名，并且类名就是别名，不再区分大小写-->
        <package name="top.zoick.domain"/>
    </typeAliases>
    
    

    <!-- 配置环境-->
    <environments default="mysql">
        <!-- 配置mysql的环境-->
        <environment id="mysql">
            <!-- 配置事务的类型-->
            <transactionManager type="jdbc"></transactionManager>
            <!-- 配置数据源（连接池）-->
            <dataSource type="POOLED">
                <!-- 配置链接数据库的四个基本信息-->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件-->
    <mappers>
<!--        <mapper resource="top/zoick/dao/IUserDao.xml"/>-->
        <!--package标签是用于指定dao接口所在的包，当指定了之后就不需要再写mapepr以及resource或者class了-->
        <package name="top.zoick.dao"/>
    </mappers>


</configuration>
```

## 项目源码：[点鸡这里](https://github.com/ArtZoick/MyBatis)

# MyBatis_3	连接池、事务控制、动态SQL

## 连接池

**１、连接池：**  
　　　　在实际开发中都会使用连接池  
　　　　因为它可以减少我们获取连接所消耗的时间   

**２、mybatis中的连接池：**  
　　　　mybatis连接池提供了3种配置方式：  
　　　　配置的位置：  
　　　　　　主配置文件SqlMapConfig.xml中的dataSource标签，type属性就是表示采用何种连接池方式。  
　　　　type属性的值：  
　　　　　　***POOLED***　　采用传统的javax.sql.DataSource规范中的连接池，mybatis中有针对规范的实现   
　　　　　　***UNPOOLED***　采用传统的获取连接的方式，虽然也实现javax.sql.DataSource接口，但是并没有使用池的思想  
　　　　　　***JNDI***　　　采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样的 
        　　　　注意：如果不是web或者maven的war工程，是不能使用的  
　　　　　　学习时使用的时tomcat服务器，采用的连接池就是dbcp连接池  

## MyBatis中的事务

### **什么是事务**  

是数据库操作的最小工作单元，是作为单个逻辑工作单元执行的一系列操作；这些操作作为一个整体一起向系统提交，要么都执行、要么都不执行；事务是一组不可再分割的操作集合（工作逻辑单元）；

### **事务的四大特性ACID**  

1 、`原子性(Atomicity)`   [ˌætəˈmɪsəti] 
事务是数据库的逻辑工作单位，事务中包含的各操作要么都做，要么都不做    

2 、`一致性(Consistency)`   [kənˈsɪstənsi]  
事务执行的结果必须是使数据库从一个一致性状态变到另一个一致性状态。因此当数据库只包含成功事务提交的结果时，就说数据库处于一致性状态。如果数据库系 运行中发生故障，有些事务尚未完成就被迫中断，这些未完成事务对数据库所做的修改有一部分已写入物理数据库，这时数据库就处于一种不正确的状态，或者说是不一致的状态。 

3 、`隔离性(Isolation)`   [ˌaɪsəˈleɪʃn]   
一个事务的执行不能其它事务干扰。即一个事务内部的操作及使用的数据对其它并发事务是隔离的，并发执行的各个事务之间不能互相干扰。 

4 、`持续性(Durability)`   [ˌdʊrəˈbɪləti]  
也称永久性，指一个事务一旦提交，它对数据库中的数据的改变就应该是永久性的。接下来的其它操作或故障不应该对其执行结果有任何影响。 

### **不考虑隔离性会产生的3个问题**  

**脏读（Dirty read）**: 当一个事务正在访问数据并且对数据进行了修改，而这种修改还没有提交到数据库中，这时另外一个事务也访问了这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据，那么另外一个事务读到的这个数据是“脏数据”，依据“脏数据”所做的操作可能是不正确的。`脏读是指在一个事务处理过程里读取了另一个未提交的事务中的数据。`

**不可重复读（Unrepeatableread）**  
指在一个事务内多次读同一数据。在这个事务还没有结束时，另一个事务也访问该数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的**修改**导致第一个事务两次读取的数据可能不太一样。这就发生了在一个事务内两次读到的数据是不一样的情况，因此称为不可重复读。不可重复读和脏读的区别是，脏读是某一事务读取了另一个事务未提交的脏数据，而不可重复读则是读取了前一事务提交的数据。`不可重复读是指在对于数据库中的某个数据，一个事务范围内多次查询却返回了不同的数据值，这是由于在查询间隔，被另一个事务修改并提交了。`

**幻读（Phantom read）**  
幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务（T2）**插入**了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录，就好像发生了幻觉一样，所以称为幻读。

### 不可重复度和幻读区别：

不可重复读的重点是**修改**，幻读的重点在于**新增或者删除**。
例1（同样的条件, 你读取过的数据, 再次读取出来发现值不一样了 ）：事务1中的A先生读取自己的工资为 1000的操作还没完成，事务2中的B先生就修改了A的工资为2000，导 致A再读自己的工资时工资变为 2000；这就是不可重复读。
例2（同样的条件, 第1次和第2次读出来的记录数不一样 ）：假某工资单表中工资大于3000的有4人，事务1读取了所有工资大于3000的人，共查到4条记录，这时事务2 又插入了一条工资大于3000的记录，事务1再次读取时查到的记录就变为了5条，这样就导致了幻读。

### **解决办法：四种隔离级别**   

**READ-UNCOMMITTED(读取未提交)**  
最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读

**READ-COMMITTED(读取已提交)**  
允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生

**REPEATABLE-READ（可重复读） **  
对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生。

**SERIALIZABLE(可串行化)**    
最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务
之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/隔离级别.png)

## 动态SQL

### if标签的使用

#### 1.接口中定义

```java
 List<User> findByCondition(User user);
```

#### 2.mapper中定义

```xml
<select id="findByCondition" resultMap="userMap" parameterType="user">
        select * from user where 1=1
        <if test="userName != null">
            and username = #{userName}
        </if>
        <if test="userSex != null">
            and sex = #{userSex}
        </if>    
</select>
```

其中： `<if test="userName != null">`这条语句中，userName值得是实体类中的属性名， and username = #{userName}这里是将实体类的userName传给数据库的username    

#### 3.测试类中测试

```java
/**
     * 测试findByCondition
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
```

### where和foreach标签的使用

用于多个查询的sql  
select * from user where id in(41,42,45)  
通过一个类中传入集合的方法  
QueryVo类  

```java
@Getter
@Setter
public class QueryVo {

    private User user;
    private List<Integer> ids;
}

```

#### 1.接口中定义

```java
    /**
     * 根据Queryvo中提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);
```

#### 2.mapper中定义

```xml
<!--根据queryvo中Id集合实现查询用户列表-->
    <select id="findUserInIds" resultMap="userMap" parameterType="queryvo">
        select * from user
        <where>
            <if test="ids != null and ids.size()>0">
                <foreach collection="ids" open="and id in (" close=")" item="uuuuid" separator=",">
                    #{uuuuid}
                </foreach>
            </if>
        </where>
    </select>
```

注意if标签中的内容都是来源于parameterType参数  

#### 3.测试代码

```java
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
```

### 抽取重复的sql

#### 1.定义

```xml
<!--抽取重复的sql语句-->
    <sql id="defaultUser">
        select * from user
    </sql>
```

#### 2.使用

```xml
<include refid="defaultUser"></include>
```

```xml
 <!--抽取重复的sql语句-->
    <sql id="defaultUser">
        select * from user
    </sql>

    <!--配置查询所有  其中id不能乱写必须是dao接口中的方法  resultType写的是实体类的全路径-->
    <select id="findAll" resultMap="userMap">
        <include refid="defaultUser"/>
    </select>
```

**注意：**尽量不要使用分号，sql语句可能会拼接

-----

# MyBatis_4	多表查询 

## 一对一的关系映射

### 示例

查询所有账户的时候同时获得当前账户的所有信息（多表查询常用方法）

### sql 语句

sql 语句查询所有账户的时候同时获得当前账户的所有信息

```sql
select u.*,a.id as aid,a.uid,a.money from account a ,user u where u.id=a.uid;
```

sql 语句查询所有账户的时候同时获得当前账户的所地址和姓名

```sql
select a.*,u.username,u.address from account a ,user u where u.id=a.uid;
```

### 1.	从表实体应该包含一个主表实体的对象引用

在Account类中

```java
//从表实体应该包含一个主表实体的对象引用
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

```

### 2.AccountDao的接口中

```java
 //查询所有账户
    List<Account> findAll();
```

### 3.AccountDao的mapper中

```xml
<mapper namespace="top.zoick.dao.IAccountDao">
    <!--定义封装account和user的resultMap-->
    <resultMap id="accountUserMap" type="account">
        <id property="id" column="aid" />
        <result property="uid" column="uid" />
        <result property="money" column="money" />
        <!--一对一的关系映射，配置封装user的内容 column中指名从表的外键 property="user"指的是单个实体类的引用-->
        <association property="user" column="uid" javaType="User">
            <id property="id" column="id"/>
            <result property="username" column="username"/>
            <result property="address" column="address"/>
            <result property="sex" column="sex"/>
            <result property="birthday" column="birthday"/>
        </association>
    </resultMap>

    <!--IAccountDao的查询所有-->
    <select id="findAll" resultMap="accountUserMap">
          select u.*,a.id as aid,a.uid,a.money from account a ,user u where u.id=a.uid;
    </select>
</mapper>
```

注意事项中的javaType=“User” 一定要指名主体表的实体类名然后column中指名从表的外键！！

```xml
<association property="user" column="uid" javaType="User">
```

### 4.AccountDao的测试类中

```java
@Test
public void findAll(){
    List<Account> accounts = accoutDao.findAll();
    for (Account account:accounts) {
       System.out.println("每一个account的信息");
       System.out.println(account);
       System.out.println(account.getUser());
     }
}
```

### 第二种方法是创建一个AccountUser类

不建议使用

-----------

## 一对多的关系映射

### 示例：用户和账户  

　　一个用户可以有多个账户  
　　一个账户只能属于一个用户（多个账户也可以属于同一个用户）  

### 步骤：   

　　1、建立两张表：用户表 帐户表  
　　　　　　让用户表和账户表之前具备一对多的关系：需要使用外键在帐户表上添加  
　　2、建立两个实体类：用户实体类和账户实体类   
　　　　　　让用户和账户的实体类能体现出来一对多的关系  
　　3、配置两个配置文件  
　　　　　　用户的配置文件  
　　　　　　账户的配置文件  
　　4、实现配置：  
　　　　　　当我们查询用户的时候，可以同时得到用户下所包含的信息  
　　　　　　当我们查询账户时，我们可以同时得到账户所属的用户信息 

### 创建表与相关数据

```sql
CREATE TABLE `account` (
  `ID` int(11) NOT NULL COMMENT '编号',
  `UID` int(11) default NULL COMMENT '用户编号',
  `MONEY` double default NULL COMMENT '金额',
  PRIMARY KEY  (`ID`),
  KEY `FK_Reference_8` (`UID`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 查询的sql语句

```sql
select * from user u left outer join account a on u.id = a.uid
```

left join（左连接）是left outer join的简写，返回左表中所有记录和右表中连接字段相等的记录，即返回的记录数和左表的记录数一样

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/7.png)

### 1.主表实体中应该包含从表实体的集合引用

User类中

```java
@Getter
@Setter
@ToString
public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    //一对多关系映射，主表实体应该包含从表实体的集合应用
    private List<Account> accounts;

}
```

### 2.UserDao的接口中

```java
//  查询所有用户
 List<User> findAll();
```

### 3.UserDao的mapper中

```xml
<!--定义封装user和account的resultMap type主表实体类-->
    <resultMap id="userAccountMap" type="User">

        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="sex" column="sex"></result>
        <result property="address" column="address"></result>
        <result property="birthday" column="birthday"></result>

        <!--一对多的关系映射，配置user封装accounts的内容-->
        <!--其中的property指的是从表的集合引用 ofType从表实体类-->
        <collection property="accounts" ofType="Account">
            <id property="id" column="aid"></id>
            <result property="uid" column="uid"></result>
            <result property="money" column="money"></result>
        </collection>
    </resultMap>

    <!--配置查询所有  其中id不能乱写必须是dao接口中的方法  -->
    <select id="findAll" resultMap="userAccountMap">
       select * from user u left outer join account a on u.id = a.uid
    </select>
```

注意：一对一用的是association，一对多以及多对多用的是collection。

### 4.UserDao的测试类中

```java
//查询所有（一个用户下的账号信息）
    @Test
    public  void findAll( ) throws Exception {
        List<User> users = userDao.findAll();
        for (User user:users
             ) {
            System.out.println("每个用户的信息");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }
```

### 5.成功运行（实现上述功能）

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/8.png)

## 多对多的关系映射

### 示例：用户与角色

一个用户可以有多个角色  
一个角色可以赋予多个用户   

### 步骤：   

　　1、建立两张表：用户表  用户表  
　　　　　　让用户和角色表具有多对多的关系。需要使用中间表，中间表中包含各自的主键，在中间表中是外键。  
　　2、建立两个实体类：用户实体类和角色实体类   
　　　　　　让用户和角色的实体类能体现出来多对多的关系  
　　　　　　各自包含对方一个集合引用  
　　3、配置两个配置文件  
　　　　　　用户的配置文件  
　　　　　　角色的配置文件  
　　4、实现配置：  
　　　　　　当我们查询用户的时候，可以同时得到用户下所包含角色的信息  
　　　　　　当我们查询角色时，我们可以同时得到角色所赋予的用户信息 

### 表设计

#### 表设置如下：

```sql
CREATE TABLE `role` (
  `ID` int(11) NOT NULL COMMENT '编号',
  `ROLE_NAME` varchar(30) default NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(60) default NULL COMMENT '角色描述',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `UID` int(11) NOT NULL COMMENT '用户编号',
  `RID` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY  (`UID`,`RID`),
  KEY `FK_Reference_10` (`RID`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`RID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```sql
insert  into `role`(`ID`,`ROLE_NAME`,`ROLE_DESC`) values (1,'院长','管理整个学院'),(2,'总裁','管理整个公司'),(3,'校长','管理整个学校');
insert  into `user_role`(`UID`,`RID`) values (41,1),(45,1),(41,2);
```

#### 表数据如下：

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/9.png)

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/9_1.png)

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/9_2.png)

#### sql语句(多表外链接查询语句)

```sql
select u.*, r.id as rid,r.role_name,r.role_desc from role r   
left outer join user_role ur on r.id = ur.rid   
left outer join user u on u.id=ur.uid  
```

注意其中每行末尾的空格  
**目的：**为了查询角色下的用户信息  
**步骤：**以role表（别名）为主表，左外连接user_role表（别名ur,此表为中间表），连接条件r.id = ur.rid 。  
再以这两个表组合的表左外连接user表，连接条件u.id=ur.uid 。

**注意：**[数据库连接（内链接，外连接（左连接，右连接）](https://jingyan.baidu.com/article/60ccbceb9578f164cab197f4.html)

#### 查询结果

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/10.png)

### 1.一个实体表中包含另一个实体表的集合引用

由于是多对多的关系所有不分从表和主表

```java
@Setter
@Getter
@ToString
public class Role implements Serializable {

    private Integer roleId;
    private String roleName;
    private String roleDesc;

    /**
     * 多对多的关系映射：一个角色可以赋予多个用户
     */
    private List<User> users;
}

```

### 2.RoleDao的接口中定义该方法

```java
List<Role> findAll();
```

### 3.RoleDao的mapper中

```xml
 <resultMap id="roleMap" type="role">
        <id property="roleId" column="rid"></id>
        <result property="roleName" column="role_name"></result>
        <result property="roleDesc" column="role_desc"></result>
        <collection property="users" ofType="user">
            <id property="id" column="id"></id>
            <result property="username" column="username"></result>
            <result property="address" column="address"></result>
            <result property="sex" column="sex"></result>
            <result property="birthday" column="birthday"></result>
        </collection>
    </resultMap>


    <select id="findAll" resultMap="roleMap">
       select u.*,r.id as rid,r.role_name,r.role_desc from role r
        left outer join user_role ur on r.id = ur.rid
        left outer join user u on u.id=ur.uid
    </select>
```

### 4.RoleDao的测试类中

```java
@Test
    public void findAll(){
        List<Role> roles  = roleDao.findAll();
        for (Role role:roles
             ) {
            System.out.println("=====每个角色的信息=====");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
```

### 5.成功运行（实现上述功能）

### ![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/10_1.png)

### ===同理可得用户的全部信息与用户的角色===

#### sql语句 

```sql
select u.*, r.id as rid,r.role_name,r.role_desc from user u
left outer join user_role ur on u.id = ur.uid
left outer join role r on r.id=ur.rid
```

### 6.实体类User包含对Accounts的引用

```java
@Getter
@Setter
@ToString
public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
    
    //实体类User包含对Accounts的引用
    private List<Role> roles;
}

```

### 7.UserDao的接口中定义该方法

```java
  //  查询所有
    List<User> findAll();
```

### 8.UserDao的mapper中

```xml
 <resultMap id="userMap" type="user">

        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>


        <collection property="roles" ofType="role">
            <id property="roleId" column="rid"></id>
            <result property="roleName" column="role_name"></result>
            <result property="roleDesc" column="role_desc"></result>
        </collection>
    </resultMap>


    <select id="findAll" resultMap="userMap">
       select u.*,r.id as rid,r.role_name,r.role_desc from user u

      left outer join user_role ur on u.id = ur.uid

       left outer join role r on r.id=ur.rid
    </select>

```

### 9.UserDao的测试类中

```
 @Test
    public  void findAll( ) throws Exception {

        List<User> users = userDao.findAll();
        for (User user:users
             ) {
            System.out.println("====每个用户的信息====");
            System.out.println(user);
            System.out.println(user.getRoles());
        }

    }
```

### 10.成功运行（实现上述功能）

---------

# JNDI

JNDI是 Java 命名与目录接口（Java Naming and Directory Interface），在J2EE规范中是重要的规范之一。

![](file://C:/Users/zoick/OneDrive/Gridea/post-images/mybatis/11.png)

----------



# MyBatis_5	延迟加载 缓存

1、mybatis中的延迟加载 
　　　　问题：在一对多中，当我们有一个用户，它有100个账户。  
　　　　　　　在查询用户的时候，要不要把关联的账户查出来？  
　　　　　　　在查询账户的时候，要不要把关联的用户查出来？  
　　　　答案：在查询用户时，用户下的账户信息是，什么时候使用，什么时候查询的。  
　　　　　　　在查询账户时，账户的所属用户信息应该是随着账户查询时一起查询出来的。  

　　　　什么是延迟加载  
　　　　　　　在真正使用数据时才发起查询，不用的时候不查询。按需加载（懒加载）  
　　　　什么是立即加载  
　　　　　　　不管用不用，只要一调用方法，马上发起查询   
　　　　在对应的四种表关系中：一对一，一对多，多对一，多对多。  
　　　　　　　一对多，多对多：通常情况下我们都是采用延迟加载。  
　　　　　　　多对一，一对一：通常情况下我们都是采用立即加载。  

## 一对一延迟加载

### 1.在SqlMapConfig.xml中配置setting标签

详情看中文官网（http://www.mybatis.org/mybatis-3/zh/configuration.html#settings）

```xml
<settings>
        <!-- 配置全局缓存-->
        <setting name="lazyLoadingEnabled" value="true"/>

        <setting name="aggressiveLazyLoading" value="true"/>
    </settings>
```

### 2.在IAccoutDao.xml中配置association标签

```xml
<!--定义封装account和user的resultMap-->
    <resultMap id="accountUserMap" type="Account">
        <id property="id" column="id"></id>

        <result property="uid" column="uid"></result>
        <result property="money" column="money"></result>

        <!--一对一的关系映射，配置封装user的内容
        select属性的内容，查询用户的唯一标识符
        column属性的内容:用户根据id查询时，所需要参数的值-->
        <association property="user" column="uid" javaType="User" select="top.zoick.dao.IUserDao.findById">
            <id property="id" column="id"></id>
            <result property="username" column="username"></result>
            <result property="sex" column="sex"></result>
            <result property="address" column="address"></result>
            <result property="birthday" column="birthday"></result>
        </association>
    </resultMap>

    <select id="findAll" resultMap="accountUserMap">
        select * from  account
    </select>
```

### 3.测试类

```java
 @Test
    public void findAll(){
        List<Account> accounts = accoutDao.findAll();
        for (Account account:
             accounts) {
            System.out.println("每一个account的信息");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
```

## 一对多延迟加载

和一对一没有太多区别

### 2.在IUserDao.xml中配置collection标签

```xml
<!--定义封装account和user的resultMap-->
    <resultMap id="accountUserMap" type="Account">
         <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="sex" column="sex"></result>
        <result property="address" column="address"></result>
        <result property="birthday" column="birthday"></result>

        <!--一对一的关系映射，配置封装user的内容
        select属性的内容，查询用户的唯一标识符
        column属性的内容:用户根据id查询时，所需要参数的值-->
    	<collection property="accounts" ofType="Account" select="com.daniel.dao.IAccoutDao.findAccountByUid" column="id">
            <id property="id" column="aid"></id>
            <result property="uid" column="uid"></result>
            <result property="money" column="money"></result>
        </collection>
    </resultMap>
```

# 缓存

2、mybatis中的缓存  
　　　　什么是缓存  
　　　　　　存在于内存中的临时数据
　　　　为什么使用缓存  
　　　　　　减少与数据库的交互次数，提高执行效率。
　　　　什么样的数据能使用缓存，什么样的数据不能使用  
　　　　　　适用于缓存：  
　　　　　　　　经常查询并且不经常改变的。  
　　　　　　　　数据的正确与否对最终结果影响不大的  
　　　　　　不适用于缓存：  
　　　　　　　　经常改变的数据。  
　　　　　　　　数据的正确与否对最终结果影响很大的。如，商品库存，银行汇率，股市牌价。  
mybatis中的一级缓存和二级缓存  
一级缓存：   
　　　　它指的是mybatis中SqlSession对象的缓存。  
　　　　当我们执行查询的时候，查询的结果会同时存入到SqlSession为我们提供的一块区域中。  
　　　　该区域的结构是一个Map。当我们再次查询同样的数据，mybatis会先去查询SqlSession中是否有，有的话直接拿来用。  
　　　　当SqlSession对象消失时，mybatis的一级缓存也就消失了。   

二级缓存：  
　　　　它指的是mybatis中SqlSessionFactory对象的缓存。由同一个SqlSessionFactory对象创建的SqlSession共享其缓存。    

　　　　二级缓存的使用步骤：  
　　　　　　　　第一步：让mybatis框架支持二级缓存（在SqlMapConfig.xml中配置）  
　　　　　　　　第二步：让当前的映射文件支持二级缓存（在IUserDao.xml中配置） 
　　　　　　　　第三步：让当前的操作支持二级缓存（在select标签中配置）　 x　　

## 一级缓存

其实mybatis中默认就是一级缓存了（平时的测试类就是一级缓存存在SqlSession中）  

**一级缓存是SqlSession范围的缓存，当调用SqlSession的修改，添加，删除，commit()，close()等方法时，就会清空一级缓存。**

## 二级缓存

### 1.SqlMaoConfig.xml中

```xml
<settings>
        <setting name="cacheEnabled" value="true"/>
</settings>
```

### 2.在需要使用二级缓存的实体类的mapper中

```xml
 <!--开启user支持二级缓存-->
    <cache/>

 <!-- 根据id查询用户   注意属性useCache -->
    <select id="findById" parameterType="INT" resultType="user" useCache="true">
        select * from user where id = #{uid}
    </select>
```

### 3.测试类

```java
public class SecondLevelCacheTest {

    private InputStream in;
    private  SqlSessionFactory factory;

    @Before//用于在测试方法执行之前执行
    public void init()throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        factory = new SqlSessionFactoryBuilder().build(in);

    }

    @After//用于在测试方法执行之后执行
    public void destroy()throws Exception{
        in.close();
    }

    /**
     * 测试二级缓存
     */
    @Test
    public void testFirstLevelCache(){
        SqlSession sqlSession1 = factory.openSession();
        IUserDao dao1 = sqlSession1.getMapper(IUserDao.class);
        User user1 = dao1.findById(41);
        System.out.println(user1);
        sqlSession1.close();//一级缓存消失

        SqlSession sqlSession2 = factory.openSession();
        IUserDao dao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = dao2.findById(41);
        System.out.println(user2);
        sqlSession2.close();

        System.out.println(user1 == user2);
    }

}
```

注意：  
从图中可以看出来第二次findbyId根本没有走数据库（数据都是从SqlSessionFactory的二级缓存中拿的 不是对象！）

# MyBatis_6	注解开发

3、mybatis中的注解开发  
　　　　环境搭建  
　　　　单表CRUD操作（代理Dao方式）  
　　　　多表查询操作  
　　　　缓存的配置  

# 注解开发的crud（不建议使用）

## 1.IUserDao接口中使用注解

```java
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user(username,address,sex,birthday)values(#{username},#{address},#{sex},#{birthday})")
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    @Update("update user set username=#{username},sex=#{sex},birthday=#{birthday},address=#{address} where id=#{id}")
    void updateUser(User user);

    /**
     * 删除用户
     * @param userId
     */
    @Delete("delete from user where id=#{id} ")
    void deleteUser(Integer userId);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user  where id=#{id} ")
    User findById(Integer userId);

    /**
     * 根据用户名称模糊查询
     * @param username
     * @return
     */
//    @Select("select * from user where username like #{username} ")
    @Select("select * from user where username like '%${value}%' ")
    List<User> findUserByName(String username);

    /**
     * 查询总用户数量
     * @return
     */
    @Select("select count(*) from user ")
    int findTotalUser();
}
```

## 2.测试类

```java
public class AnnotationCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public  void init()throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        userDao = session.getMapper(IUserDao.class);
    }

    @After
    public  void destroy()throws  Exception{
        session.commit();
        session.close();
        in.close();
    }


    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("mybatis annotation");
        user.setAddress("北京市昌平区");

        userDao.saveUser(user);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(57);
        user.setUsername("mybatis annotation update");
        user.setAddress("北京市海淀区");
        user.setSex("男");
        user.setBirthday(new Date());

        userDao.updateUser(user);
    }


    @Test
    public void testDelete(){
        userDao.deleteUser(51);
    }

    @Test
    public void testFindOne(){
        User user = userDao.findById(57);
        System.out.println(user);
    }


    @Test
    public  void testFindByName(){
//        List<User> users = userDao.findUserByName("%mybatis%");
        List<User> users = userDao.findUserByName("mybatis");
        for(User user : users){
            System.out.println(user);
        }
    }

    @Test
    public  void testFindTotal(){
        int total = userDao.findTotalUser();
        System.out.println(total);
    }
}
```

# 注解开发的多表查询

可以这么记忆：  
你所需要对应的表是很多个注解用many  
对应一个的话就用one   

demo功能阐述:  
查询用户账户的时候 返回用户的信息  
因为多个账户或者一个账户才对应一个用户

## 多对一（一对一）

### 1.IAccountDao接口中使用注解

```java
public interface IAccountDao {

    /**
     * 查询所有账户，并且获取每个账户所属的用户信息
     * @return
     */
    @Select("select * from account")
    @Results(id="accountMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "money",property = "money"),
            //这个注解是引入主表        FetchType(加载时机)  EAGER(立即加载)
            @Result(property = "user",column = "uid",one=@One(select="com.itheima.dao.IUserDao.findById",fetchType= FetchType.EAGER))
    })
    List<Account> findAll();

    /**
     * 根据用户id查询账户信息
     * @param userId
     * @return
     */
    @Select("select * from account where uid = #{userId}")
    List<Account> findAccountByUid(Integer userId);
}
```

### 2.Account类中（从表类）

```java
 //多对一（mybatis中称之为一对一）的映射：一个账户只能属于一个用户
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
```

### 3.测试类

```java
public class AccountTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IAccountDao accountDao;

    @Before
    public  void init()throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        accountDao = session.getMapper(IAccountDao.class);
    }

    @After
    public  void destroy()throws  Exception{
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public  void  testFindAll(){
        List<Account> accounts = accountDao.findAll();
        for(Account account : accounts){
            System.out.println("----每个账户的信息-----");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }

}

```

## 一对多（多对多）

demo功能阐述  
查询用户信息的时候 返回用户的账户信息  
因为一个用户对应多个用户的账户信息    

使用的是延迟加载

### 1.IUserDao接口中使用注解

```java
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user")
    @Results(id="userMap",value={
            @Result(id=true,column = "id",property = "userId"),
            @Result(column = "username",property = "userName"),
            @Result(column = "address",property = "userAddress"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(property = "accounts",column = "id",
                    many = @Many(select = "com.itheima.dao.IAccountDao.findAccountByUid",
                                fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user  where id=#{id} ")
    @ResultMap("userMap")
    User findById(Integer userId);

    /**
     * 根据用户名称模糊查询
     * @param username
     * @return
     */
    @Select("select * from user where username like #{username} ")
    @ResultMap("userMap")
    List<User> findUserByName(String username);

}
```

### 2.User类中（从表类）

```java
 //一对多关系映射：一个用户对应多个账户
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
```

# 注解开启二级缓存

哪个Dao接口需要就写在哪儿

```java
@CacheNamespace(blocking = true)
```

