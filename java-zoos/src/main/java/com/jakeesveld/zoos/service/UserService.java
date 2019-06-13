package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(long userid);

    void delete(long userid);

    User save(User user);

    User update(User user, long userid);
}
