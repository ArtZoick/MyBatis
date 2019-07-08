package top.zoick.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@ToString
public class User implements Serializable {
    private Integer id;
    private String name;
    private Date birthday;
    private String sex;
    private String address;
}
