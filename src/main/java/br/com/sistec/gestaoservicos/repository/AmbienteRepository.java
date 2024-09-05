package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Ambiente;
import br.com.sistec.gestaoservicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {
}
