package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
