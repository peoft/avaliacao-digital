/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a")
    , @NamedQuery(name = "Administrador.findByUsuarioCodigo", query = "SELECT a FROM Administrador a WHERE a.usuarioCodigo = :usuarioCodigo")
    , @NamedQuery(name = "Administrador.findByTempoUso", query = "SELECT a FROM Administrador a WHERE a.tempoUso = :tempoUso")
    , @NamedQuery(name = "Administrador.findByUltimaOperacao", query = "SELECT a FROM Administrador a WHERE a.ultimaOperacao = :ultimaOperacao")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuarioCodigo")
    private Integer usuarioCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tempoUso")
    @Temporal(TemporalType.TIME)
    private Date tempoUso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ultimaOperacao")
    private String ultimaOperacao;
    @JoinColumn(name = "usuarioCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public Administrador() {
    }

    public Administrador(Integer usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public Administrador(Integer usuarioCodigo, Date tempoUso, String ultimaOperacao) {
        this.usuarioCodigo = usuarioCodigo;
        this.tempoUso = tempoUso;
        this.ultimaOperacao = ultimaOperacao;
    }

    public Integer getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(Integer usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    public Date getTempoUso() {
        return tempoUso;
    }

    public void setTempoUso(Date tempoUso) {
        this.tempoUso = tempoUso;
    }

    public String getUltimaOperacao() {
        return ultimaOperacao;
    }

    public void setUltimaOperacao(String ultimaOperacao) {
        this.ultimaOperacao = ultimaOperacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioCodigo != null ? usuarioCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.usuarioCodigo == null && other.usuarioCodigo != null) || (this.usuarioCodigo != null && !this.usuarioCodigo.equals(other.usuarioCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Administrador[ usuarioCodigo=" + usuarioCodigo + " ]";
    }
    
}
