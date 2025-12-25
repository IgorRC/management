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
        // --- 1. DEFINICIÓN DE PERMISOS ---
        // Módulo Seguridad
        Permission pSecUserRead = createPermissionIfNotFound("SEC_USER_READ");
        Permission pSecUserCreate = createPermissionIfNotFound("SEC_USER_CREATE");

        // Módulo RRHH (Recursos Humanos)
        Permission pHrEmpRead = createPermissionIfNotFound("HR_EMPLOYEE_READ");
        Permission pHrEmpCreate = createPermissionIfNotFound("HR_EMPLOYEE_CREATE");
        Permission pHrEmpEdit = createPermissionIfNotFound("HR_EMPLOYEE_UPDATE");
        Permission pHrSalaryRead = createPermissionIfNotFound("HR_SALARY_READ"); // ¡Sensible!

        // Permisos Generales
        Permission pMyProfile = createPermissionIfNotFound("MY_PROFILE_READ");

        // --- 2. DEFINICIÓN DE ROLES (Agrupación) ---

        // ROL: ADMIN (Todo poderoso)
        createRoleIfNotFound("ROLE_ADMIN", Set.of(
                pSecUserRead, pSecUserCreate,
                pHrEmpRead, pHrEmpCreate, pHrEmpEdit, pHrSalaryRead,
                pMyProfile
        ));

        // ROL: GERENTE RRHH (Ve todo lo de RRHH, incluyendo sueldos)
        createRoleIfNotFound("ROLE_HR_MANAGER", Set.of(
                pHrEmpRead, pHrEmpCreate, pHrEmpEdit, pHrSalaryRead,
                pMyProfile, pSecUserRead
        ));

        // ROL: ASISTENTE RRHH (Operativo, NO ve sueldos)
        createRoleIfNotFound("ROLE_HR_ASSISTANT", Set.of(
                pHrEmpRead, pHrEmpCreate, pHrEmpEdit,
                pMyProfile
        ));

        // ROL: EMPLEADO (Base)
        Role roleEmployee = createRoleIfNotFound("ROLE_EMPLOYEE", Set.of(pMyProfile));
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
