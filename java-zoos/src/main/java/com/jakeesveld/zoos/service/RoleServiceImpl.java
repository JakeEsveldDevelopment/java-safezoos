package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.Role;
import com.jakeesveld.zoos.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
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

    @Transactional
    @Modifying
    @Override
    public void delete(long roleid) {
        if(repo.findById(roleid).isPresent()){
            repo.deleteById(roleid);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Modifying
    @Override
    public Role save(Role role) {
        return repo.save(role);
    }
}
