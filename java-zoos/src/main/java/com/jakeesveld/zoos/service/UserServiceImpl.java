package com.jakeesveld.zoos.service;

import com.jakeesveld.zoos.model.User;
import com.jakeesveld.zoos.model.UserRoles;
import com.jakeesveld.zoos.repo.RoleRepository;
import com.jakeesveld.zoos.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository rolerepos;

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

    @Transactional
    @Modifying
    @Override
    public void delete(long userid) {
        if(repo.findById(userid).isPresent()){
            repo.deleteById(userid);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Modifying
    @Override
    public User save(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);


        return repo.save(newUser);
    }

    @Transactional
    @Modifying
    @Override
    public User update(User user, long userid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = repo.findByUsername(authentication.getName());

        if (currentUser != null)
        {
            if (userid == currentUser.getUserid())
            {
                if (user.getUsername() != null)
                {
                    currentUser.setUsername(user.getUsername());
                }

                if (user.getPassword() != null)
                {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }

                if (user.getUserRoles().size() > 0)
                {
                    rolerepos.deleteUserRolesById(currentUser.getUserid());

                    for (UserRoles ur : user.getUserRoles())
                    {
                        rolerepos.insertUserRole(userid, ur.getRole().getRoleid());
                    }
                }

                return repo.save(currentUser);
            }
            else
            {
                throw new EntityNotFoundException(Long.toString(userid) + " Not current user");
            }
        }
        else
        {
            throw new EntityNotFoundException(authentication.getName());
        }

    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repo.findByUsername(s);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }
}
