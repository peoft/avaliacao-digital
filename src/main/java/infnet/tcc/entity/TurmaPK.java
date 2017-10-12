/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author peof
 */
@Embeddable
public class TurmaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fim;

    public TurmaPK() {
    }

    public TurmaPK(int codigo, Date inicio, Date fim) {
        this.codigo = codigo;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigo;
        hash += (inicio != null ? inicio.hashCode() : 0);
        hash += (fim != null ? fim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurmaPK)) {
            return false;
        }
        TurmaPK other = (TurmaPK) object;
        if (this.codigo != other.codigo) {
            return false;
        }
        if ((this.inicio == null && other.inicio != null) || (this.inicio != null && !this.inicio.equals(other.inicio))) {
            return false;
        }
        if ((this.fim == null && other.fim != null) || (this.fim != null && !this.fim.equals(other.fim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.TurmaPK[ codigo=" + codigo + ", inicio=" + inicio + ", fim=" + fim + " ]";
    }
    
}
