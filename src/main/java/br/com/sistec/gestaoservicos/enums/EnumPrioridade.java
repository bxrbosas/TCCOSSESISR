package br.com.sistec.gestaoservicos.enums;

public enum EnumPrioridade {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");
    private String descricao;

    EnumPrioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
