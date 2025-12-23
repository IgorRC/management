package com.gniuscode.sie.management.security.repository;

import com.gniuscode.sie.management.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
