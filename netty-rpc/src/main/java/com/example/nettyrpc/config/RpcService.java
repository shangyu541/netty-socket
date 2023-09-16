package com.example.nettyrpc.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉<br>
 * 〈注解〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
//用于接口和类上
@Target(ElementType.TYPE)
//在运行时可以获取到
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
}
