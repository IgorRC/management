package com.gniuscode.sie.management.common.config;

import com.gniuscode.sie.management.security.entity.Permission;
import com.gniuscode.sie.management.security.entity.Role;
import com.gniuscode.sie.management.security.repository.PermissionRepository;
import com.gniuscode.sie.management.security.repository.RoleRepository;
import com.gniuscode.sie.management.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
            System.out.println("Iniciando Banco de Dados");
            seedSecurityData();
            System.out.println("Seeder Terminado exitosamente");
        };
    }

    @Transactional
    public void seedSecurityData(){
        Permission permMetric = createPermissionIfNotFound("VIEW_METRICS");
        Permission permUsers = createPermissionIfNotFound("MANAGE_USERS");
        Permission permReports = createPermissionIfNotFound("VIEW_REPORTS");

        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN", Set.of(permMetric, permUsers, permReports));
        Role roleUser = createRoleIfNotFound("ROLE_USER", Set.of(permUsers, permReports));

    }

    //metodos aux
    private Permission createPermissionIfNotFound(String name){
        return permissionRepository.findByName(name)
                .orElseGet(() -> {
                    Permission permission = new Permission();
                    permission.setName(name);
                    return permissionRepository.save(permission);
                });
    }

    private Role  createRoleIfNotFound(String name, Set<Permission> permissions){
        return roleRepository.findByName(name)
                .orElseGet(()->{
                    Role role = new Role();
                    role.setName(name);
                    role.setPermissions(permissions);
                    return roleRepository.save(role);
                });
    }
}
