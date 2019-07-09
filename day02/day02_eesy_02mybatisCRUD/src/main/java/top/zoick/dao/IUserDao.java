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
     * 保存用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据删除用户
     *
     * @param userId
     */
    void deleteUser(Integer userId);

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