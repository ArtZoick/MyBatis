package top.zoick.mybatis.io;

import java.io.InputStream;

/**
 * @author zoick
 * @date 2019/7/5 16:03
 * 使用类加载器读取配置文件的类
 */
public class Resources {
    /**
     * 根据传入的参数，获取一个字节输入流
     * @param filepath
     * @return
     */
    public static InputStream getResourceAsStream(String filepath){
        /*
            class拿到字节码 getClassLoader拿到类加载器
                getResourceAsStream(filepath) 根据类加载器读取filepath配置
         */
        return Resources.class.getClassLoader().getResourceAsStream(filepath);
    }
}
