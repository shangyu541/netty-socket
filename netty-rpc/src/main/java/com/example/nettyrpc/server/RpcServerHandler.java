package com.example.nettyrpc.server;

import com.alibaba.fastjson.JSON;
import com.example.nettyrpc.config.RpcService;
import com.example.nettyrpc.model.RpcRequest;
import com.example.nettyrpc.model.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
@Component
//设置通道共享
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    private static final Map SERVICE_INSTANCE_MAP = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o) throws Exception {
        //接收客户端请求-将msg转化RpcRequest对象
        RpcRequest rpcRequest = JSON.parseObject(o, RpcRequest.class);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            rpcResponse.setResult(handler(rpcRequest));
        }catch (Exception e){
            e.printStackTrace();
            rpcResponse.setError(e.getMessage());
        }
        channelHandlerContext.writeAndFlush(JSON.toJSONString(rpcResponse));
    }

    /**
     * 业务处理逻辑
     * @param rpcRequest
     * @return
     */
    private Object handler(RpcRequest rpcRequest) throws InvocationTargetException {
        Object serviceBean = SERVICE_INSTANCE_MAP.get(rpcRequest.getClassName());
        if (serviceBean==null){
            throw new RuntimeException("根据beanname找不到服务，beanName:"+rpcRequest.getClassName());
        }
        //解析请求中的方法名称，参数类型，参数信息
        Class<?> aClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        //反射调用bean的方法-cglib反射调用
        FastClass fastClass = FastClass.create(aClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean,parameters);
    }

    /**
     * 将标有@RpcService注解的bean缓存
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceMap!=null&& serviceMap.size()>0){
            Set<Map.Entry<String, Object>> entries = serviceMap.entrySet();
            for (Map.Entry item: entries
                 ) {
                Object serviceBean = item.getValue();
                if (serviceBean.getClass().getInterfaces().length==0){
                    throw new RuntimeException("服务必须实现接口");
                }
                //默认取第一个接口作为缓存bean的名称
                String name = serviceBean.getClass().getInterfaces()[0].getName();
                SERVICE_INSTANCE_MAP.put(name,serviceBean);
            }
        }
    }


}
