package top.zoick.mybatis.sqlsession.proxy;

import top.zoick.mybatis.cfg.Mapper;
import top.zoick.mybatis.utils.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

/**
 * @author zoick
 * @date 2019/7/5 22:13
 */
public class MapperProxy implements InvocationHandler {

    //map的key是全限定类名＋方法名
    private Map<String, Mapper> mappers;
    private Connection conn;

    public MapperProxy(Map<String, Mapper> mappers,Connection conn) {
        this.conn = conn;
        this.mappers = mappers;
    }


    /**
     * 用户对方法的增强，即selectlist方法
     * @param proxy  the proxy instance that the method was invoked on
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1.获取方法名
        String methodName = method.getName();
        //2.获取方法所在类的名称
        String classname = method.getDeclaringClass().getName();
        //3.组合key
        String key =  classname+ "." + methodName;
        //4.获取mapper中的Mapper对象
        Mapper mapper = mappers.get(key);
        //5.判断是否有mapper
        if(mapper == null){
            throw new IllegalArgumentException("传入参数有误");
        }
        //6.调用工具类查询所有
        return new Executor().selectList(mapper,conn);
    }
}
