package br.com.sistec.gestaoservicos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String description;

    private String localidade;

    private String image;

    @OneToMany (mappedBy = "ambiente")
    private List<Servico> servicos;
}
