package br.com.sistec.gestaoservicos.repository;

import br.com.sistec.gestaoservicos.model.Funcionario;
import br.com.sistec.gestaoservicos.model.Pessoa;
import br.com.sistec.gestaoservicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Array;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    // Executa Stored Procedure ImprimirServicosporMes() com query nativa
    @Query(value = "CALL ImprimirQuantidadeServicosporMes()", nativeQuery = true)
    List<Object[]> imprimirServicosPorMes();

    List<Servico> findByFuncionario(Funcionario funcionario);

}
