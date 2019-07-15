package top.zoick.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

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
