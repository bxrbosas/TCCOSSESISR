package br.com.sistec.gestaoservicos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa {
    private Double salario;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}