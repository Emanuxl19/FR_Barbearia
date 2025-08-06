package com.barbearia.api.security.config;

import com.barbearia.api.model.Barber;
import com.barbearia.api.model.Role;
import com.barbearia.api.repository.BarberRepository;
import com.barbearia.api.repository.RoleRepository;
import com.barbearia.api.security.model.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Componente para popular o banco de dados com dados iniciais na inicialização da aplicação.
 * Cria os papéis (Roles) e um usuário administrador padrão.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BarberRepository barberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cria os papéis básicos se eles não existirem
        createRoleIfNotFound(RoleName.ADMIN);
        createRoleIfNotFound(RoleName.BARBER);
        createRoleIfNotFound(RoleName.CLIENT);

        // Cria um usuário administrador se ele não existir
        if (barberRepository.findByEmail("admin@email.com").isEmpty()) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Erro: Papel ADMIN não encontrado."));

            Barber adminUser = Barber.builder()
                    .name("Admin")
                    .email("admin@email.com")
                    .password(passwordEncoder.encode("123456")) // Use uma senha forte em produção
                    .available(true)
                    .roles(Set.of(adminRole))
                    .build();

            barberRepository.save(adminUser);
            System.out.println("Usuário ADMIN padrão criado com sucesso.");
        }
    }

    /**
     * Verifica se um papel existe e o cria caso contrário.
     * @param roleName O nome do papel a ser verificado/criado.
     */
    private void createRoleIfNotFound(RoleName roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(Role.builder().name(roleName).build());
            System.out.println("Papel " + roleName + " criado.");
        }
    }
}
