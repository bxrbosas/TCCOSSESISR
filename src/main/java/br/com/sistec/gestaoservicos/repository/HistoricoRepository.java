package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
}