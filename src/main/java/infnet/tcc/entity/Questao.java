/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "questao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questao.findAll", query = "SELECT q FROM Questao q")
    , @NamedQuery(name = "Questao.findByCodigo", query = "SELECT q FROM Questao q WHERE q.codigo = :codigo")
    , @NamedQuery(name = "Questao.findByTexto", query = "SELECT q FROM Questao q WHERE UPPER(q.texto) = UPPER(:texto)")
    , @NamedQuery(name = "Questao.findByCriacao", query = "SELECT q FROM Questao q WHERE q.criacao = :criacao")
    , @NamedQuery(name = "Questao.findByModificacao", query = "SELECT q FROM Questao q WHERE q.modificacao = :modificacao")
    , @NamedQuery(name = "Questao.findByTextoDifferentFromCurrent", query = "SELECT t FROM Questao t WHERE UPPER(t.texto) = UPPER(:texto) and t.codigo != :codigo")})
public class Questao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @Column(name = "criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date criacao;
    @Basic(optional = false)
    @Column(name = "modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificacao;
    @JoinTable(name = "topicoquestao", joinColumns = {
    @JoinColumn(name = "questaoCodigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
    @JoinColumn(name = "topicoCodigo", referencedColumnName = "codigo")})
    @ManyToMany(fetch = FetchType.EAGER)    
    private Collection<Topico> topicoCollection;

    public Questao() {
        topicoCollection = new HashSet<Topico>();
    }

    public Questao(Integer codigo) {
        this.codigo = codigo;
    }

    public Questao(Integer codigo, String texto, Date criacao, Date modificacao) {
        this.codigo = codigo;
        this.texto = texto;
        this.criacao = criacao;
        this.modificacao = modificacao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public Date getModificacao() {
        return modificacao;
    }

    public void setModificacao(Date modificacao) {
        this.modificacao = modificacao;
    }

    @XmlTransient
    public Collection<Topico> getTopicoCollection() {
        return topicoCollection;
    }

    public void setTopicoCollection(Collection<Topico> topicoCollection) {
        this.topicoCollection = topicoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questao)) {
            return false;
        }
        Questao other = (Questao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Questao[ codigo=" + codigo + " ]";
    }
    
}
