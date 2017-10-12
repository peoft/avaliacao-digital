/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "resposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resposta.findAll", query = "SELECT r FROM Resposta r")
    , @NamedQuery(name = "Resposta.findByCodigo", query = "SELECT r FROM Resposta r WHERE r.respostaPK.codigo = :codigo")
    , @NamedQuery(name = "Resposta.findByFormularioCodigo", query = "SELECT r FROM Resposta r WHERE r.respostaPK.formularioCodigo = :formularioCodigo")
    , @NamedQuery(name = "Resposta.findByResposta", query = "SELECT r FROM Resposta r WHERE r.resposta = :resposta")})
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespostaPK respostaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "resposta")
    private String resposta;
    @JoinColumn(name = "formularioCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Formulario formulario;

    public Resposta() {
    }

    public Resposta(RespostaPK respostaPK) {
        this.respostaPK = respostaPK;
    }

    public Resposta(RespostaPK respostaPK, String resposta) {
        this.respostaPK = respostaPK;
        this.resposta = resposta;
    }

    public Resposta(int codigo, int formularioCodigo) {
        this.respostaPK = new RespostaPK(codigo, formularioCodigo);
    }

    public RespostaPK getRespostaPK() {
        return respostaPK;
    }

    public void setRespostaPK(RespostaPK respostaPK) {
        this.respostaPK = respostaPK;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respostaPK != null ? respostaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resposta)) {
            return false;
        }
        Resposta other = (Resposta) object;
        if ((this.respostaPK == null && other.respostaPK != null) || (this.respostaPK != null && !this.respostaPK.equals(other.respostaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Resposta[ respostaPK=" + respostaPK + " ]";
    }
    
}
