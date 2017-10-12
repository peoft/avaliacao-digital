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
public class TurmaalunoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "alunoCPF")
    private long alunoCPF;
    @Basic(optional = false)
    @NotNull
    @Column(name = "turmaCodigo")
    private int turmaCodigo;

    public TurmaalunoPK() {
    }

    public TurmaalunoPK(long alunoCPF, int turmaCodigo) {
        this.alunoCPF = alunoCPF;
        this.turmaCodigo = turmaCodigo;
    }

    public long getAlunoCPF() {
        return alunoCPF;
    }

    public void setAlunoCPF(long alunoCPF) {
        this.alunoCPF = alunoCPF;
    }

    public int getTurmaCodigo() {
        return turmaCodigo;
    }

    public void setTurmaCodigo(int turmaCodigo) {
        this.turmaCodigo = turmaCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) alunoCPF;
        hash += (int) turmaCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurmaalunoPK)) {
            return false;
        }
        TurmaalunoPK other = (TurmaalunoPK) object;
        if (this.alunoCPF != other.alunoCPF) {
            return false;
        }
        if (this.turmaCodigo != other.turmaCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.TurmaalunoPK[ alunoCPF=" + alunoCPF + ", turmaCodigo=" + turmaCodigo + " ]";
    }
    
}
