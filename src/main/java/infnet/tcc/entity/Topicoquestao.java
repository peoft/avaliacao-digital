/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "topicoquestao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topicoquestao.findAll", query = "SELECT t FROM Topicoquestao t")
    , @NamedQuery(name = "Topicoquestao.findByQuestaoCodigo", query = "SELECT t FROM Topicoquestao t WHERE t.questaoCodigo = :questaoCodigo")})
public class Topicoquestao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "questaoCodigo")
    private Integer questaoCodigo;
    @JoinColumn(name = "questaoCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Questao questao;
    @JoinColumn(name = "topicoCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Topico topicoCodigo;

    public Topicoquestao() {
    }

    public Topicoquestao(Integer questaoCodigo) {
        this.questaoCodigo = questaoCodigo;
    }

    public Integer getQuestaoCodigo() {
        return questaoCodigo;
    }

    public void setQuestaoCodigo(Integer questaoCodigo) {
        this.questaoCodigo = questaoCodigo;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public Topico getTopicoCodigo() {
        return topicoCodigo;
    }

    public void setTopicoCodigo(Topico topicoCodigo) {
        this.topicoCodigo = topicoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questaoCodigo != null ? questaoCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Topicoquestao)) {
            return false;
        }
        Topicoquestao other = (Topicoquestao) object;
        if ((this.questaoCodigo == null && other.questaoCodigo != null) || (this.questaoCodigo != null && !this.questaoCodigo.equals(other.questaoCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Topicoquestao[ questaoCodigo=" + questaoCodigo + " ]";
    }
    
}
