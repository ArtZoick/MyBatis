package top.zoick.dao;

import top.zoick.domain.Role;

import java.util.List;

/**
 * @author zoick
 * @date 2019/7/12 14:10
 */
public interface IRoleDao {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();
}
