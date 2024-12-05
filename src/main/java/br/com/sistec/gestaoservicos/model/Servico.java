package br.com.sistec.gestaoservicos.model;

import br.com.sistec.gestaoservicos.enums.EnumPrioridade;
import br.com.sistec.gestaoservicos.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dsDemanda;
    private String local;
    private String descricao;
    private String image;
    private EnumPrioridade prioridade;
    private Date dataAbertura = new Date( System.currentTimeMillis() );
    private EnumStatus status;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "historico_servico",

            joinColumns = @JoinColumn(name = "servico_id"),
            inverseJoinColumns = @JoinColumn(name = "historico_id")
    )
    private List<Historico> historicos = new ArrayList<Historico>();

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.sql.Date prazoServico;

    @ManyToOne
    private Ambiente ambiente;

    @ManyToOne
    private Funcionario funcionario;

}
