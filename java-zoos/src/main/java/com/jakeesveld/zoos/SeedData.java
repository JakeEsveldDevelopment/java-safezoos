package com.jakeesveld.zoos;
import com.jakeesveld.zoos.model.Role;
import com.jakeesveld.zoos.model.User;
import com.jakeesveld.zoos.model.UserRoles;
import com.jakeesveld.zoos.repo.RoleRepository;
import com.jakeesveld.zoos.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("ADMIN");
        Role r2 = new Role("ZOODATA");
        Role r3 = new Role("ANIMALDATA");
        Role r4 = new Role("MGR");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));

        ArrayList<UserRoles> zooData = new ArrayList<>();
        zooData.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> animalData = new ArrayList<>();
        animalData.add(new UserRoles(new User(), r3));

        ArrayList<UserRoles> mgr = new ArrayList<>();
        mgr.add(new UserRoles(new User(), r4));


        rolerepos.save(r1);
        rolerepos.save(r2);
        rolerepos.save(r3);
        rolerepos.save(r4);

        User u1 = new User("JimBob", "myNameIsJimBob", zooData);
        User u2 = new User("admin", "password", admins);
        User u3 = new User("Turkey Leg", "TheBestSnack", mgr);
        User u4 = new User("Legless Turkey", "SomeoneStoleIt", animalData);

        userrepos.save(u1);
        userrepos.save(u2);
        userrepos.save(u3);
        userrepos.save(u4);
    }
}