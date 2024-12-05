package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
