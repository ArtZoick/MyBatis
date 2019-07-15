package top.zoick.dao;

import top.zoick.domain.User;

import java.util.List;

/**
 * @author zoick
 * @date 2019/7/4 17:34
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有操作,同时获取到用户下所有账户的信息
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



}