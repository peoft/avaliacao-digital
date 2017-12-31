/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author peof
 */
@Embeddable
public class RespostaPK implements Serializable {    
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formularioCodigo")
    private Integer formularioCodigo;

    public RespostaPK() {
    }

    public RespostaPK(Integer codigo, Integer formularioCodigo) {
        this.codigo = codigo;
        this.formularioCodigo = formularioCodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getFormularioCodigo() {
        return formularioCodigo;
    }

    public void setFormularioCodigo(Integer formularioCodigo) {
        this.formularioCodigo = formularioCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        hash = 89 * hash + Objects.hashCode(this.formularioCodigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RespostaPK other = (RespostaPK) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.formularioCodigo, other.formularioCodigo)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "infnet.tcc.RespostaPK[ codigo=" + codigo + ", formularioCodigo=" + formularioCodigo + " ]";
    }
    
}
