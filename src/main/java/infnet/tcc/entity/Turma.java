/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "turma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t")
    , @NamedQuery(name = "Turma.findByCodigo", query = "SELECT t FROM Turma t WHERE t.turmaPK.codigo = :codigo")
    , @NamedQuery(name = "Turma.findByInicio", query = "SELECT t FROM Turma t WHERE t.turmaPK.inicio = :inicio")
    , @NamedQuery(name = "Turma.findByFim", query = "SELECT t FROM Turma t WHERE t.turmaPK.fim = :fim")
    , @NamedQuery(name = "Turma.findByDescricao", query = "SELECT t FROM Turma t WHERE t.descricao = :descricao")})
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TurmaPK turmaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao")
    private String descricao;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "turmaCollection")
    private Collection<Aluno> alunoCollection;
    @JoinColumn(name = "moduloCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Modulo moduloCodigo;
    @JoinColumn(name = "professorCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Professor professorCodigo;

    public Turma() {
        alunoCollection = new HashSet<Aluno>();
    }

    public Turma(TurmaPK turmaPK) {
        this.turmaPK = turmaPK;
    }

    public Turma(TurmaPK turmaPK, String descricao) {
        this.turmaPK = turmaPK;
        this.descricao = descricao;
    }

    public Turma(int codigo, Date inicio, Date fim) {
        this.turmaPK = new TurmaPK(codigo, inicio, fim);
    }

    public TurmaPK getTurmaPK() {
        return turmaPK;
    }

    public void setTurmaPK(TurmaPK turmaPK) {
        this.turmaPK = turmaPK;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Aluno> getAlunoCollection() {
        return alunoCollection;
    }

    public void setTurmaalunoCollection(Collection<Aluno> alunoCollection) {
        this.alunoCollection = alunoCollection;
    }

    public Modulo getModuloCodigo() {
        return moduloCodigo;
    }

    public void setModuloCodigo(Modulo moduloCodigo) {
        this.moduloCodigo = moduloCodigo;
    }

    public Professor getProfessorCodigo() {
        return professorCodigo;
    }

    public void setProfessorCodigo(Professor professorCodigo) {
        this.professorCodigo = professorCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turmaPK != null ? turmaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.turmaPK == null && other.turmaPK != null) || (this.turmaPK != null && !this.turmaPK.equals(other.turmaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Turma[ turmaPK=" + turmaPK + " ]";
    }
    
}
