package com.ducninh.freshfood.repository;

import com.ducninh.freshfood.model.ERole;
import com.ducninh.freshfood.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
