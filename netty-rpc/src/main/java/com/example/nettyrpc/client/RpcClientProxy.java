package com.example.nettyrpc.client;

import com.alibaba.fastjson.JSON;
import com.example.nettyrpc.model.RpcRequest;
import com.example.nettyrpc.model.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈客户端代理类-创建代理对象〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
public class RpcClientProxy {

    /**
     * 封装request请求对象
     * 创建rpcclient对象
     * 发送消息
     */
    public static Object creatProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //  封装request请求对象
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(UUID.randomUUID().toString());
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                //创建rpcclient对象
                RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                try {
                    //发送消息
                    Object send = rpcClient.send(JSON.toJSONString(rpcRequest));
                    RpcResponse rpcResponse = JSON.parseObject(send.toString(), RpcResponse.class);
                    if (rpcResponse.getError() != null) {
                        throw new RuntimeException(rpcResponse.getError());
                    }
                    //返回结果
                    Object result = rpcResponse.getResult();
                    return JSON.parseObject(result.toString(), method.getReturnType());
                } catch (Exception e) {
                    throw e;
                } finally {
                    rpcClient.close();
                }
            }
        });
    }

}
