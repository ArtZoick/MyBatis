package top.zoick.dao;

import top.zoick.domain.Account;

import java.util.List;

/**
 * @author zoick
 * @date 2019/7/11 15:41
 */
public interface IAccountDao {

    /**
     * 查询所有账户
     * @return
     */
    List<Account> findAll();
}
