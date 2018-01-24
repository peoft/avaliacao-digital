/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author peof
 */
@Entity
@Table(name = "formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulario.findAll", query = "SELECT f FROM Formulario f")
    , @NamedQuery(name = "Formulario.findByCodigo", query = "SELECT f FROM Formulario f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "Formulario.findByNomeAluno", query = "SELECT f FROM Formulario f WHERE f.nomeAluno = :nomeAluno")
    , @NamedQuery(name = "Formulario.findByNomeModulo", query = "SELECT f FROM Formulario f WHERE f.nomeModulo = :nomeModulo")
    , @NamedQuery(name = "Formulario.findByAvaliacaoAndAluno", query = "SELECT f FROM Formulario f WHERE f.avaliacao.codigo = :avaliacaoCodigo and f.alunoCodigo.codigo = :alunoCodigo")
    , @NamedQuery(name = "Formulario.findByComentariosSugestoes", query = "SELECT f FROM Formulario f WHERE f.comentariosSugestoes = :comentariosSugestoes")})
public class Formulario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Size(max = 50)
    @Column(name = "nomeAluno")
    private String nomeAluno;
    @Column(name = "cpfAluno")
    private BigInteger cpfAluno;
    @Size(max = 50)
    @Column(name = "nomeModulo")
    private String nomeModulo;
    @Size(max = 500)
    @Column(name = "comentariosSugestoes")
    private String comentariosSugestoes;
    @JoinColumn(name = "alunoCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Aluno alunoCodigo;
    @JoinColumn(name = "avaliacaoCodigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Avaliacao avaliacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulario")
    private Collection<Resposta> respostaCollection;

    public Formulario() {
        respostaCollection = new ArrayList<>();
    }

    public Formulario(Integer codigo) {
        this.codigo = codigo;
    }

    public Formulario(Integer codigo, String nomeModulo) {
        this.codigo = codigo;
        this.nomeModulo = nomeModulo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public BigInteger getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(BigInteger cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public String getNomeModulo() {
        return nomeModulo;
    }

    public void setNomeModulo(String nomeModulo) {
        this.nomeModulo = nomeModulo;
    }

    public String getComentariosSugestoes() {
        return comentariosSugestoes;
    }

    public void setComentariosSugestoes(String comentariosSugestoes) {
        this.comentariosSugestoes = comentariosSugestoes;
    }

    public Aluno getAlunoCodigo() {
        return alunoCodigo;
    }

    public void setAlunoCodigo(Aluno alunoCodigo) {
        this.alunoCodigo = alunoCodigo;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @XmlTransient
    public Collection<Resposta> getRespostaCollection() {
        return respostaCollection;
    }

    public void setRespostaCollection(Collection<Resposta> respostaCollection) {
        this.respostaCollection = respostaCollection;
    }
    
    public void addReposta(Resposta resposta) {
        respostaCollection.add(resposta);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.codigo);
        hash = 29 * hash + Objects.hashCode(this.nomeAluno);
        hash = 29 * hash + Objects.hashCode(this.cpfAluno);
        hash = 29 * hash + Objects.hashCode(this.nomeModulo);
        hash = 29 * hash + Objects.hashCode(this.comentariosSugestoes);
        hash = 29 * hash + Objects.hashCode(this.alunoCodigo);
        hash = 29 * hash + Objects.hashCode(this.avaliacao);
        hash = 29 * hash + Objects.hashCode(this.respostaCollection);
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
        final Formulario other = (Formulario) obj;
        if (!Objects.equals(this.nomeAluno, other.nomeAluno)) {
            return false;
        }
        if (!Objects.equals(this.nomeModulo, other.nomeModulo)) {
            return false;
        }
        if (!Objects.equals(this.comentariosSugestoes, other.comentariosSugestoes)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.cpfAluno, other.cpfAluno)) {
            return false;
        }
        if (!Objects.equals(this.alunoCodigo, other.alunoCodigo)) {
            return false;
        }
        if (!Objects.equals(this.avaliacao, other.avaliacao)) {
            return false;
        }
        if (!Objects.equals(this.respostaCollection, other.respostaCollection)) {
            return false;
        }
        return true;
    }

    
}
