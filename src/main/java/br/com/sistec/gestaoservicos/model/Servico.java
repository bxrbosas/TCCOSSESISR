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
    private Date dataAbertura = new Date( System.currentTimeMillis() );
    private EnumStatus status;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Historico> historicos = new ArrayList<Historico>();

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
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

    public void addHistorico(Historico historico) {
        this.historicos.add(historico);
    }
    public void removeHistorico(Historico historico) {
        this.historicos.remove(historico);
    }
}

