package br.com.sistec.gestaoservicos.model;

import br.com.sistec.gestaoservicos.enums.EnumEstadoCivil;
import br.com.sistec.gestaoservicos.enums.EnumPrioridade;
import br.com.sistec.gestaoservicos.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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
    private EnumStatus status;
    private Date dataAbertura = new Date( System.currentTimeMillis());
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Historico> historicos = new ArrayList<Historico>();
    @Basic
    @Temporal(TemporalType.DATE)
    private java.sql.Date prazoServico;
    @ManyToOne
    private Ambiente ambiente;

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }


    public java.sql.Date getPrazoServico() {
        return prazoServico;
    }

    public void setPrazoServico(java.sql.Date prazoServico) {
        this.prazoServico = prazoServico;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDsDemanda() {
        return dsDemanda;
    }

    public void setDsDemanda(String dsDemanda) {
        this.dsDemanda = dsDemanda;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EnumPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(EnumPrioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public EnumStatus getStatus() {
        return status;
    }
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    // retorna o nome do ambiente
    public String getNomeAmbiente(){
        return this.ambiente.getNome();
    }
}

