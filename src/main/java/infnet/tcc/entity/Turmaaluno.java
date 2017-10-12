/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "turmaaluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turmaaluno.findAll", query = "SELECT t FROM Turmaaluno t")
    , @NamedQuery(name = "Turmaaluno.findByAlunoCPF", query = "SELECT t FROM Turmaaluno t WHERE t.turmaalunoPK.alunoCPF = :alunoCPF")
    , @NamedQuery(name = "Turmaaluno.findByTurmaCodigo", query = "SELECT t FROM Turmaaluno t WHERE t.turmaalunoPK.turmaCodigo = :turmaCodigo")})
public class Turmaaluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TurmaalunoPK turmaalunoPK;
    @ManyToMany(mappedBy = "turmaalunoCollection")
    private Collection<Avaliacao> avaliacaoCollection;
    @JoinColumn(name = "alunoCPF", referencedColumnName = "cpf", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aluno aluno;
    //@JoinColumn(name = "turmaCodigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @JoinColumns({
        @JoinColumn(name = "turmaPKCodigo", referencedColumnName = "codigo"),
        @JoinColumn(name = "turmaPKInicio", referencedColumnName = "inicio"),
        @JoinColumn(name = "turmaPKFim", referencedColumnName = "fim")
    })    
    @ManyToOne(optional = false)
    private Turma turma;

    public Turmaaluno() {
    }

    public Turmaaluno(TurmaalunoPK turmaalunoPK) {
        this.turmaalunoPK = turmaalunoPK;
    }

    public Turmaaluno(long alunoCPF, int turmaCodigo) {
        this.turmaalunoPK = new TurmaalunoPK(alunoCPF, turmaCodigo);
    }

    public TurmaalunoPK getTurmaalunoPK() {
        return turmaalunoPK;
    }

    public void setTurmaalunoPK(TurmaalunoPK turmaalunoPK) {
        this.turmaalunoPK = turmaalunoPK;
    }

    @XmlTransient
    public Collection<Avaliacao> getAvaliacaoCollection() {
        return avaliacaoCollection;
    }

    public void setAvaliacaoCollection(Collection<Avaliacao> avaliacaoCollection) {
        this.avaliacaoCollection = avaliacaoCollection;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turmaalunoPK != null ? turmaalunoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turmaaluno)) {
            return false;
        }
        Turmaaluno other = (Turmaaluno) object;
        if ((this.turmaalunoPK == null && other.turmaalunoPK != null) || (this.turmaalunoPK != null && !this.turmaalunoPK.equals(other.turmaalunoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Turmaaluno[ turmaalunoPK=" + turmaalunoPK + " ]";
    }
    
}
