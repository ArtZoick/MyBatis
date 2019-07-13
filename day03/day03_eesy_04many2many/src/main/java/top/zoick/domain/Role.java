package top.zoick.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author zoick
 * @date 2019/7/12 14:02
 */
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
