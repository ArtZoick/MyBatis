package top.zoick.mybatis.annotation;

import top.zoick.domain.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @author zoick
 * @date 2019/7/8 14:34
 * 查询的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {

    /**
     * 配置sql语句的
     * @return
     */
    String value();
}
