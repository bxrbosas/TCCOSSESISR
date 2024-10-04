package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Array;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
