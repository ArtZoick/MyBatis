package top.zoick.mybatis.cfg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zoick
 * @date 2019/7/5 16:42
 *自定义mybatis的配置类
 */
@Setter
@Getter
@ToString
public class Configuration {

    private String driver;
    private String url;
    private String username;
    private String password;

    private Map<String,Mapper> mappers = new HashMap<String, Mapper>();

    public void setMappers(Map<String, Mapper> mappers) {
        this.mappers.putAll(mappers);//此处需要使用追加的方式
    }
}
