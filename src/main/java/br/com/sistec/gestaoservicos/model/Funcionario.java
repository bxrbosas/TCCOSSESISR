package br.com.sistec.gestaoservicos.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa {
    private Double salario;
}