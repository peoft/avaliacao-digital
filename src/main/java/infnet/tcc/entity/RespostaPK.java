/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author peof
 */
@Embeddable
public class RespostaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formularioCodigo")
    private int formularioCodigo;

    public RespostaPK() {
    }

    public RespostaPK(int codigo, int formularioCodigo) {
        this.codigo = codigo;
        this.formularioCodigo = formularioCodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getFormularioCodigo() {
        return formularioCodigo;
    }

    public void setFormularioCodigo(int formularioCodigo) {
        this.formularioCodigo = formularioCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigo;
        hash += (int) formularioCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespostaPK)) {
            return false;
        }
        RespostaPK other = (RespostaPK) object;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (this.formularioCodigo != other.formularioCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.RespostaPK[ codigo=" + codigo + ", formularioCodigo=" + formularioCodigo + " ]";
    }
    
}
