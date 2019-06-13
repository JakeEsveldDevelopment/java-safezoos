package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.Role;
import com.jakeesveld.zoos.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repo;

    @Override
    public List<Role> findall() {
        List<Role> rolesList = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(rolesList::add);
        return rolesList;
    }

    @Override
    public Role findById(long roleid) {
        return repo.findById(roleid).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(long roleid) {
        if(repo.findById(roleid).isPresent()){
            repo.deleteById(roleid);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Role save(Role role) {
        return repo.save(role);
    }
}
