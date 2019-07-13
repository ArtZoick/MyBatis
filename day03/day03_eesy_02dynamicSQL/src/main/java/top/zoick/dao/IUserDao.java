package top.zoick.dao;

import org.apache.ibatis.annotations.Param;
import top.zoick.domain.QueryVo;
import top.zoick.domain.User;

import java.util.List;

/**
 * @author zoick
 * @date 2019/7/4 17:34
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有操作
     *
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询用户信息
     *
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
     * 根据QueryVo中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入参数的条件：有可能有用户名，有可能有性别，也有可能有地址，还有可能都有
     * @param user
     * @return
     */
    List<User> findByCondition(User user);


    /**
     * 根据Queryvo中提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);


}