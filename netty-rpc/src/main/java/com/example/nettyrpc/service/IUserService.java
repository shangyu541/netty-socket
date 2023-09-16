package com.example.nettyrpc.service;

import com.example.nettyrpc.model.User;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
public interface IUserService {
    User getByID(int id);
}
