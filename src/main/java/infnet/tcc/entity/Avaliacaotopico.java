/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "avaliacaotopico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avaliacaotopico.findAll", query = "SELECT a FROM Avaliacaotopico a")
    , @NamedQuery(name = "Avaliacaotopico.findByCodigo", query = "SELECT a FROM Avaliacaotopico a WHERE a.avaliacaotopicoPK.codigo = :codigo")
    , @NamedQuery(name = "Avaliacaotopico.findByAvaliacaoCodigo", query = "SELECT a FROM Avaliacaotopico a WHERE a.avaliacaotopicoPK.avaliacaoCodigo = :avaliacaoCodigo")
    , @NamedQuery(name = "Avaliacaotopico.findByTopicoCodigo", query = "SELECT a FROM Avaliacaotopico a WHERE a.avaliacaotopicoPK.topicoCodigo = :topicoCodigo")})
public class Avaliacaotopico implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AvaliacaotopicoPK avaliacaotopicoPK;
    @JoinColumn(name = "avaliacaoCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Avaliacao avaliacao;
    @JoinColumn(name = "topicoCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Topico topico;

    public Avaliacaotopico() {
    }

    public Avaliacaotopico(AvaliacaotopicoPK avaliacaotopicoPK) {
        this.avaliacaotopicoPK = avaliacaotopicoPK;
    }

    public Avaliacaotopico(int codigo, String avaliacaoCodigo, int topicoCodigo) {
        this.avaliacaotopicoPK = new AvaliacaotopicoPK(codigo, avaliacaoCodigo, topicoCodigo);
    }

    public AvaliacaotopicoPK getAvaliacaotopicoPK() {
        return avaliacaotopicoPK;
    }

    public void setAvaliacaotopicoPK(AvaliacaotopicoPK avaliacaotopicoPK) {
        this.avaliacaotopicoPK = avaliacaotopicoPK;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (avaliacaotopicoPK != null ? avaliacaotopicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacaotopico)) {
            return false;
        }
        Avaliacaotopico other = (Avaliacaotopico) object;
        if ((this.avaliacaotopicoPK == null && other.avaliacaotopicoPK != null) || (this.avaliacaotopicoPK != null && !this.avaliacaotopicoPK.equals(other.avaliacaotopicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Avaliacaotopico[ avaliacaotopicoPK=" + avaliacaotopicoPK + " ]";
    }
    
}
