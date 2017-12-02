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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "avaliacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avaliacao.findAll", query = "SELECT a FROM Avaliacao a")
    , @NamedQuery(name = "Avaliacao.findByCodigo", query = "SELECT a FROM Avaliacao a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "Avaliacao.findById", query = "SELECT a FROM Avaliacao a WHERE UPPER(a.id) = UPPER(:id)")
    , @NamedQuery(name = "Avaliacao.findByObjetivo", query = "SELECT a FROM Avaliacao a WHERE a.objetivo = :objetivo")
    , @NamedQuery(name = "Avaliacao.findByInicio", query = "SELECT a FROM Avaliacao a WHERE a.inicio = :inicio")
    , @NamedQuery(name = "Avaliacao.findByTermino", query = "SELECT a FROM Avaliacao a WHERE a.termino = :termino")
    , @NamedQuery(name = "Avaliacao.findByCriacao", query = "SELECT a FROM Avaliacao a WHERE a.criacao = :criacao")
    , @NamedQuery(name = "Avaliacao.findByModificacao", query = "SELECT a FROM Avaliacao a WHERE a.modificacao = :modificacao")
    , @NamedQuery(name = "Avaliacao.findByTextoConvidativo", query = "SELECT a FROM Avaliacao a WHERE a.textoConvidativo = :textoConvidativo")
    , @NamedQuery(name = "Avaliacao.findByLinkPagina", query = "SELECT a FROM Avaliacao a WHERE a.linkPagina = :linkPagina")
    , @NamedQuery(name = "Avaliacao.findByTextoDifferentFromCurrent", query = "SELECT t FROM Avaliacao t WHERE UPPER(t.id) = UPPER(:id) and t.codigo != :codigo")})        
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "objetivo")
    private String objetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date termino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date criacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificacao;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "logoPath")
    private String logoPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "textoConvidativo")
    private String textoConvidativo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "linkPagina")
    private String linkPagina;
    @JoinTable(name = "avaliacaotopico", joinColumns = {
        @JoinColumn(name = "avaliacaoCodigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "topicoCodigo", referencedColumnName = "codigo")})
    @ManyToMany
    private Collection<Topico> topicoCollection;
    @JoinTable(name = "avaliacaoturmaaluno", joinColumns = {
        @JoinColumn(name = "avaliacaoCodigo", referencedColumnName = "codigo")}, 
        inverseJoinColumns = {
            @JoinColumn(name = "turmaCodigo", referencedColumnName = "codigo")
        })
    @ManyToMany
    private Collection<Turma> avaliacaoTurmaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avaliacaoCodigo")
    private Collection<Formulario> formularioCollection;

    public Avaliacao() {
        topicoCollection = new HashSet<>();
        formularioCollection = new HashSet<>();
        avaliacaoTurmaCollection = new HashSet<>();
    }

    public Avaliacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Avaliacao(Integer codigo, String id, String objetivo, Date inicio, Date termino, Date criacao, Date modificacao, String logoPath, String textoConvidativo, String linkPagina) {
        this.codigo = codigo;
        this.id = id;
        this.objetivo = objetivo;
        this.inicio = inicio;
        this.termino = termino;
        this.criacao = criacao;
        this.modificacao = modificacao;
        this.logoPath = logoPath;
        this.textoConvidativo = textoConvidativo;
        this.linkPagina = linkPagina;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public Date getModificacao() {
        return modificacao;
    }

    public void setModificacao(Date modificacao) {
        this.modificacao = modificacao;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getTextoConvidativo() {
        return textoConvidativo;
    }

    public void setTextoConvidativo(String textoConvidativo) {
        this.textoConvidativo = textoConvidativo;
    }

    public String getLinkPagina() {
        return linkPagina;
    }

    public void setLinkPagina(String linkPagina) {
        this.linkPagina = linkPagina;
    }

    @XmlTransient
    public Collection<Topico> getTopicoCollection() {
        return topicoCollection;
    }

    public void setTopicoCollection(Collection<Topico> topicoCollection) {
        this.topicoCollection = topicoCollection;
    }

    @XmlTransient
    public Collection<Turma> getAvaliacaoTurmaCollection() {
        return avaliacaoTurmaCollection;
    }

    public void setAvaliacaoTurmaCollection(Collection<Turma> turmaCollection) {
        this.avaliacaoTurmaCollection = turmaCollection;
    }

    @XmlTransient
    public Collection<Formulario> getFormularioCollection() {
        return formularioCollection;
    }

    public void setFormularioCollection(Collection<Formulario> formularioCollection) {
        this.formularioCollection = formularioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacao)) {
            return false;
        }
        Avaliacao other = (Avaliacao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "infnet.tcc.entity.Avaliacao[ codigo=" + codigo + " ]";
    }
    
}
