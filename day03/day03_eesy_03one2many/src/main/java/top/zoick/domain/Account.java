package top.zoick.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zoick
 * @date 2019/7/11 15:39
 */
@Getter
@Setter
@ToString
public class Account {

    private Integer id;
    private Integer uid;
    private Double money;

    /**
     * 从表实体应该包含一个主表实体的对象引用
     */
    private User user;



}
