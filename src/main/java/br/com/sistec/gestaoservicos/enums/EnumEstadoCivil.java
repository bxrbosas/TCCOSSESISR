package br.com.sistec.gestaoservicos.enums;

public enum EnumEstadoCivil {
    SOLTEIRO("Solteiro"),
    CASADO("Casado"),
    DIVORCIADO("Divorciado"),
    VIUVO("Viuvo");

    private String descricao;

    EnumEstadoCivil(String descricao) {
        this.descricao = descricao;
    }
}
