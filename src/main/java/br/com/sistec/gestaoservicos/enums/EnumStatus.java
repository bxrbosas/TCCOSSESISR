package br.com.sistec.gestaoservicos.enums;

public enum EnumStatus {
    ABERTA("Aberta"),
    PENDENTE("Pendente"),
    FECHADA("Fechada");
    private String descricao;

    EnumStatus (String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {return descricao;}
}
