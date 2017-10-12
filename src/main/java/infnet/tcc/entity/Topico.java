/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "topico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topico.findAll", query = "SELECT t FROM Topico t")
    , @NamedQuery(name = "Topico.findByCodigo", query = "SELECT t FROM Topico t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "Topico.findByTitulo", query = "SELECT t FROM Topico t WHERE t.titulo = :titulo")})
public class Topico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo")
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicoCodigo")
    private Collection<Topicoquestao> topicoquestaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topico")
    private Collection<Avaliacaotopico> avaliacaotopicoCollection;

    public Topico() {
    }

    public Topico(Integer codigo) {
        this.codigo = codigo;
    }

    public Topico(Integer codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public Collection<Topicoquestao> getTopicoquestaoCollection() {
        return topicoquestaoCollection;
    }

    public void setTopicoquestaoCollection(Collection<Topicoquestao> topicoquestaoCollection) {
        this.topicoquestaoCollection = topicoquestaoCollection;
    }

    @XmlTransient
    public Collection<Avaliacaotopico> getAvaliacaotopicoCollection() {
        return avaliacaotopicoCollection;
    }

    public void setAvaliacaotopicoCollection(Collection<Avaliacaotopico> avaliacaotopicoCollection) {
        this.avaliacaotopicoCollection = avaliacaotopicoCollection;
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
        if (!(object instanceof Topico)) {
            return false;
        }
        Topico other = (Topico) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Topico[ codigo=" + codigo + " ]";
    }
    
}
