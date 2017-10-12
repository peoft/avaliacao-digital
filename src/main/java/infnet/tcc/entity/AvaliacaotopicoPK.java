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
import javax.validation.constraints.Size;

/**
 *
 * @author peof
 */
@Embeddable
public class AvaliacaotopicoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "avaliacaoCodigo")
    private String avaliacaoCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "topicoCodigo")
    private int topicoCodigo;

    public AvaliacaotopicoPK() {
    }

    public AvaliacaotopicoPK(int codigo, String avaliacaoCodigo, int topicoCodigo) {
        this.codigo = codigo;
        this.avaliacaoCodigo = avaliacaoCodigo;
        this.topicoCodigo = topicoCodigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAvaliacaoCodigo() {
        return avaliacaoCodigo;
    }

    public void setAvaliacaoCodigo(String avaliacaoCodigo) {
        this.avaliacaoCodigo = avaliacaoCodigo;
    }

    public int getTopicoCodigo() {
        return topicoCodigo;
    }

    public void setTopicoCodigo(int topicoCodigo) {
        this.topicoCodigo = topicoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigo;
        hash += (avaliacaoCodigo != null ? avaliacaoCodigo.hashCode() : 0);
        hash += (int) topicoCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvaliacaotopicoPK)) {
            return false;
        }
        AvaliacaotopicoPK other = (AvaliacaotopicoPK) object;
        if (this.codigo != other.codigo) {
            return false;
        }
        if ((this.avaliacaoCodigo == null && other.avaliacaoCodigo != null) || (this.avaliacaoCodigo != null && !this.avaliacaoCodigo.equals(other.avaliacaoCodigo))) {
            return false;
        }
        if (this.topicoCodigo != other.topicoCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.AvaliacaotopicoPK[ codigo=" + codigo + ", avaliacaoCodigo=" + avaliacaoCodigo + ", topicoCodigo=" + topicoCodigo + " ]";
    }
    
}
