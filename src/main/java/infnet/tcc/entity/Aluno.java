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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
    , @NamedQuery(name = "Aluno.findByCpf", query = "SELECT a FROM Aluno a WHERE a.cpf = :cpf")
    , @NamedQuery(name = "Aluno.findByNome", query = "SELECT a FROM Aluno a WHERE a.nome = :nome")
    , @NamedQuery(name = "Aluno.findByEMail", query = "SELECT a FROM Aluno a WHERE a.eMail = :eMail")
    , @NamedQuery(name = "Aluno.findByMatricula", query = "SELECT a FROM Aluno a WHERE a.matricula = :matricula")
    , @NamedQuery(name = "Aluno.findByNomeMae", query = "SELECT a FROM Aluno a WHERE a.nomeMae = :nomeMae")
    , @NamedQuery(name = "Aluno.findByDtNascimento", query = "SELECT a FROM Aluno a WHERE a.dtNascimento = :dtNascimento")
    , @NamedQuery(name = "Aluno.findByCarteiraIdentidade", query = "SELECT a FROM Aluno a WHERE a.carteiraIdentidade = :carteiraIdentidade")
    , @NamedQuery(name = "Aluno.findBySexo", query = "SELECT a FROM Aluno a WHERE a.sexo = :sexo")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpf")
    private Long cpf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "eMail")
    private String eMail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "matricula")
    private int matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nomeMae")
    private String nomeMae;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dtNascimento")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "carteiraIdentidade")
    private long carteiraIdentidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "sexo")
    private String sexo;
    @JoinColumn(name = "usuarioCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Usuario usuarioCodigo;
    @JoinTable(name = "turmaAluno", joinColumns = {
        @JoinColumn(name = "alunoCPF", referencedColumnName = "cpf")}, inverseJoinColumns = {
        @JoinColumn(name = "turmaCodigo", referencedColumnName = "codigo"),
        @JoinColumn(name = "turmaInicio", referencedColumnName = "inicio"),
        @JoinColumn(name = "turmaFim", referencedColumnName = "fim")
    })
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Turma> turmaCollection;

    public Aluno() {
        turmaCollection = new HashSet<Turma>();
    }

    public Aluno(Long cpf) {
        this.cpf = cpf;
    }

    public Aluno(Long cpf, String nome, String eMail, int matricula, String nomeMae, Date dtNascimento, long carteiraIdentidade, String sexo) {
        this.cpf = cpf;
        this.nome = nome;
        this.eMail = eMail;
        this.matricula = matricula;
        this.nomeMae = nomeMae;
        this.dtNascimento = dtNascimento;
        this.carteiraIdentidade = carteiraIdentidade;
        this.sexo = sexo;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public long getCarteiraIdentidade() {
        return carteiraIdentidade;
    }

    public void setCarteiraIdentidade(long carteiraIdentidade) {
        this.carteiraIdentidade = carteiraIdentidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Usuario getUsuarioCodigo() {
        return usuarioCodigo;
    }

    public void setUsuarioCodigo(Usuario usuarioCodigo) {
        this.usuarioCodigo = usuarioCodigo;
    }

    @XmlTransient
    public Collection<Turma> getTurmaalunoCollection() {
        return turmaCollection;
    }

    public void setTurmaalunoCollection(Collection<Turma> turmaCollection) {
        this.turmaCollection = turmaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.Aluno[ cpf=" + cpf + " ]";
    }
    
}
