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
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    , @NamedQuery(name = "Turma.findAllByPeriod", query = "SELECT t FROM Turma t WHERE :period >= t.inicio and :period <= t.fim")
    , @NamedQuery(name = "Turma.findByCodigo", query = "SELECT t FROM Turma t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "Turma.findByInicio", query = "SELECT t FROM Turma t WHERE t.inicio = :inicio")
    , @NamedQuery(name = "Turma.findByFim", query = "SELECT t FROM Turma t WHERE t.fim = :fim")
    , @NamedQuery(name = "Turma.findByDescricao", query = "SELECT t FROM Turma t WHERE t.descricao = :descricao")})
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao")
    private String descricao;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "turmaCollection")
    private Collection<Aluno> alunoCollection;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "avalicaoTurmaCollection")
    private Collection<Avaliacao> avaliacaoCollection;
    @JoinColumn(name = "moduloCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Modulo moduloCodigo;
    @JoinColumn(name = "professorCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Professor professorCodigo;

    public Turma() {
        alunoCollection = new HashSet<Aluno>();
        avaliacaoCollection = new HashSet<Avaliacao>();
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

    public Collection<Avaliacao> getAvaliacaoCollection() {
        return avaliacaoCollection;
    }

    public void setAvaliacaoCollection(Collection<Avaliacao> avaliacaoCollection) {
        this.avaliacaoCollection = avaliacaoCollection;
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
        int hash = 7;
        hash = 13 * hash + this.codigo;
        hash = 13 * hash + Objects.hashCode(this.inicio);
        hash = 13 * hash + Objects.hashCode(this.fim);
        hash = 13 * hash + Objects.hashCode(this.descricao);
        hash = 13 * hash + Objects.hashCode(this.alunoCollection);
        hash = 13 * hash + Objects.hashCode(this.avaliacaoCollection);
        hash = 13 * hash + Objects.hashCode(this.moduloCodigo);
        hash = 13 * hash + Objects.hashCode(this.professorCodigo);
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
        final Turma other = (Turma) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.inicio, other.inicio)) {
            return false;
        }
        if (!Objects.equals(this.fim, other.fim)) {
            return false;
        }
        if (!Objects.equals(this.alunoCollection, other.alunoCollection)) {
            return false;
        }
        if (!Objects.equals(this.avaliacaoCollection, other.avaliacaoCollection)) {
            return false;
        }
        if (!Objects.equals(this.moduloCodigo, other.moduloCodigo)) {
            return false;
        }
        if (!Objects.equals(this.professorCodigo, other.professorCodigo)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return this.descricao;
    }

}
