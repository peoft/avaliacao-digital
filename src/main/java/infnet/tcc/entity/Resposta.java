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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    , @NamedQuery(name = "Resposta.getReports", query = "SELECT r FROM Resposta r")
    , @NamedQuery(name = "Resposta.findByCodigo", query = "SELECT r FROM Resposta r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "Resposta.findByFormularioCodigo", query = "SELECT r FROM Resposta r WHERE r.formulario.codigo = :formularioCodigo")
    , @NamedQuery(name = "Resposta.findByResposta", query = "SELECT r FROM Resposta r WHERE r.resposta = :resposta")})
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;        
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "resposta")
    private String resposta;
    @JoinColumn(name = "questaoCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Questao questaoCodigo;
    @ManyToOne
    @JoinColumn(name = "formularioCodigo", referencedColumnName = "codigo")
    private Formulario formulario;
    

    public Resposta() {
    }

    public Resposta(String resposta) {
        this.resposta = resposta;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }    

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Questao getQuestaoCodigo() {
        return questaoCodigo;
    }

    public void setQuestaoCodigo(Questao questaoCodigo) {
        this.questaoCodigo = questaoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigo);
        hash = 29 * hash + Objects.hashCode(this.resposta);
        hash = 29 * hash + Objects.hashCode(this.questaoCodigo);
        hash = 29 * hash + Objects.hashCode(this.formulario);
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
        final Resposta other = (Resposta) obj;
        if (!Objects.equals(this.resposta, other.resposta)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.questaoCodigo, other.questaoCodigo)) {
            return false;
        }
        if (!Objects.equals(this.formulario, other.formulario)) {
            return false;
        }
        return true;
    }
}
