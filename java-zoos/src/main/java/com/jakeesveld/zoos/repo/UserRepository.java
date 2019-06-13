package com.jakeesveld.zoos.repo;

import com.jakeesveld.zoos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
