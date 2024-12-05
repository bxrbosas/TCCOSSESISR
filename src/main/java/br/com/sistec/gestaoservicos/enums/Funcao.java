package br.com.sistec.gestaoservicos.enums;

import lombok.Getter;

@Getter
public enum Funcao {

    ADMINISTRADOR("Administrador"),
    REQUISITANTE("Requisitante"),
    EXECUTOR("Executor");

    private final String descricao;

    Funcao(String descricao){
        this.descricao = descricao;
    }

}
