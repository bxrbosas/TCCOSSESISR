package br.com.sistec.gestaoservicos.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

@DiscriminatorValue(value = "F")

public class Funcionario extends Pessoa {
    private Double salario;
}