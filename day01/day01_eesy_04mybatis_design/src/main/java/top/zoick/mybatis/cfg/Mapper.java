package top.zoick.mybatis.cfg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zoick
 * @date 2019/7/5 16:50
 * 用于封装执行的SQL语句和结果集的全限定类名
 */
@Getter
@Setter
public class Mapper {

    private String queryString;//SQL
    private String resultType;//实体类的全限定类名
}
