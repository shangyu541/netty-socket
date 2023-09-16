package com.example.nettyrpc.client;

import com.example.nettyrpc.model.User;
import com.example.nettyrpc.service.IUserService;

/**
 * 〈一句话功能简述〉<br>
 * 〈客户端启动类〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.creatProxy(IUserService.class);
        User user = userService.getByID(1);
        System.out.println(user);
    }
}
