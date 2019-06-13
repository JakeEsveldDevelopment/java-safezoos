package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.User;
import com.jakeesveld.zoos.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public User findById(long userid) {
        return repo.findById(userid).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(long userid) {
        if(repo.findById(userid).isPresent()){
            repo.deleteById(userid);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public User update(User user, long userid) {
        User currentUser = repo.findById(userid).orElseThrow(EntityNotFoundException::new);
        if(user.getUsername() != null){
            currentUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null){
            currentUser.setPassword(user.getPassword());
        }
        if (user.getUserRoles() != null){
            currentUser.getUserRoles().addAll(user.getUserRoles());
        }
        return currentUser;
    }
}
