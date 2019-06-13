package com.jakeesveld.zoos.repo;

import com.jakeesveld.zoos.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM userroles WHERE userid=:userid", nativeQuery = true)
    void deleteUserRolesById(long userid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO userroles(userid, roleid) VALUES (:userid, :roleid)", nativeQuery = true)
    void insertUserRole(long userid, long roleid);

}
