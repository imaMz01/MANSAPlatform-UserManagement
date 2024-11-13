package com.mansa.authorization.Repository;

import com.mansa.authorization.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByRole(String role);
}
