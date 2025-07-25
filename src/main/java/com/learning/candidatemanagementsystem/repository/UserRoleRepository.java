package com.learning.candidatemanagementsystem.repository;

import com.learning.candidatemanagementsystem.model.UserRole;
import com.learning.candidatemanagementsystem.model.keys.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
