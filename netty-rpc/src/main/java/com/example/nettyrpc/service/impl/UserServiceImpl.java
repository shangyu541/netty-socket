package com.example.nettyrpc.service.impl;

import com.example.nettyrpc.config.RpcService;
import com.example.nettyrpc.model.User;
import com.example.nettyrpc.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
@RpcService
@Service
public class UserServiceImpl implements IUserService {

    Map<Object, User> userMap = new HashMap<>();

    @Override
    public User getByID(int id) {
        if (userMap.size() == 0) {
            User user = new User();
            user.setId(1);
            user.setName("张三");
            User user2 = new User();
            user2.setId(2);
            user2.setName("李四");
            userMap.put(user.getId(), user);
            userMap.put(user2.getId(), user2);
        }
        return userMap.get(id);
    }
}
