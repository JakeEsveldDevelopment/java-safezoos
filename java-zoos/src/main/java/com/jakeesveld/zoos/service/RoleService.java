package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findall();

    Role findById(long roleid);

    void delete(long roleid);

    Role save(Role role);
}
