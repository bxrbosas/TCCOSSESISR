package br.com.sistec.gestaoservicos.model;

import br.com.sistec.gestaoservicos.enums.Funcao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa {
    private Double salario;
    private Funcao funcao;
}