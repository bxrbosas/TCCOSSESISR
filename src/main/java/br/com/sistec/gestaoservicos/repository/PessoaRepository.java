package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
