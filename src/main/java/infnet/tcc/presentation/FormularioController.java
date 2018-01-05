package infnet.tcc.presentation;

import infnet.tcc.entity.Aluno;
import infnet.tcc.entity.Avaliacao;
import infnet.tcc.entity.Formulario;
import infnet.tcc.entity.Questao;
import infnet.tcc.entity.Resposta;
import infnet.tcc.entity.Topico;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.FormularioFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("formularioController")
@SessionScoped
public class FormularioController implements Serializable {

    private Formulario current;
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.FormularioFacade ejbFacade;
    private PaginationHelper pagination;
    @EJB
    private infnet.tcc.facade.AvaliacaoFacade ejbAvaliacaoFacade;
    private List<String> comentariosSugestoes;
    private Integer alunoCodigo;
    private Integer avaliacaoCodigo;
    private Avaliacao avaliacao;
    private static Collection<FormularioResposta> respostas;
    @EJB
    private infnet.tcc.facade.TopicoFacade ejbTopicoFacade;
    @EJB
    private infnet.tcc.facade.QuestaoFacade ejbQuestaoFacade;

    public FormularioController() {
        if (comentariosSugestoes == null) {
            comentariosSugestoes = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) {
                comentariosSugestoes.add("");
            }
        }
    }

    public Collection<FormularioResposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(Collection<FormularioResposta> respostas) {
        this.respostas = respostas;
    }

    public Integer getAvaliacaoCodigo() {
        return avaliacaoCodigo;
    }

    public void setAvaliacaoCodigo(Integer avaliacaoCodigo) {
        this.avaliacaoCodigo = avaliacaoCodigo;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getAlunoCodigo() {
        return alunoCodigo;
    }

    public void setAlunoCodigo(Integer alunoCodigo) {
        this.alunoCodigo = alunoCodigo;
    }

    public Formulario getSelected() {
        if (current == null) {            
            current = new Formulario();
            findAvaliacao();
        }
        return current;
    }

    public List<String> getComentariosSugestoes() {
        return comentariosSugestoes;
    }

    public void setComentariosSugestoes(List<String> comentariosSugestoes) {
        this.comentariosSugestoes = comentariosSugestoes;
    }

    public void findAvaliacao() {
        Integer codigo = (Integer) getParameter("codigo");
        if (codigo != null) {
            Avaliacao avaliacaoFound = ejbAvaliacaoFacade.find(codigo);
            getSelected();
            current.setAvaliacao(avaliacaoFound);
            this.setAvaliacao(avaliacaoFound);
            this.setAvaliacaoCodigo(codigo);
        }
        setAlunoCodigo();
        setCodigoQuestoesFromAvaliacao();
    }

    private void setCodigoQuestoesFromAvaliacao() {
        if (current != null) {
            if (respostas != null) {
                respostas = null;
            }

            respostas = new ArrayList<>();
            if (current != null) {
                Avaliacao avaliacaoGot = current.getAvaliacao();

                for (Topico topico : avaliacaoGot.getTopicoCollection()) {
                    Collection<Questao> questoes = getQuestoesByTopicoCodigo(topico.getCodigo());
                    for (Questao questao : questoes) {
                        FormularioResposta resposta = new FormularioResposta(topico.getCodigo(), questao.getCodigo(), questao.getTexto(), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
                        respostas.add(resposta);
                    }
                }
            }
        }
    }

    private void setAlunoCodigo() {
        Integer codigo = (Integer) getParameter("id");
        if (codigo != null) {
            alunoCodigo = codigo;
        }
    }

    private Object getParameter(String parameter) {
        String requestParameter = JsfUtil.getRequestParameter(parameter);
        Integer codigo = null;
        if (requestParameter != null && !requestParameter.isEmpty()) {
            codigo = new Integer(requestParameter);
        }
        return codigo;
    }

    private FormularioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String create() {
        try {
            if (!existsFormularioForAluno()) {
                if (isTodasRespostasPreenchidas()) {
                    Aluno aluno = new Aluno();

                    aluno.setCodigo(alunoCodigo);
                    current.setAlunoCodigo(aluno);
                    current.setComentariosSugestoes(comentariosSugestoes.toString().replaceAll("[\\]\\[]", ""));
                    setRespostasInEntidade();
                    getFacade().create(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormularioCreated"));
                    return prepareCreate();
                } else {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NotAllAnswersAsked"));
                    return null;
                }
            } else {
                return prepareCreate();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareCreate() {
        current = null;
        alunoCodigo = null;
        avaliacaoCodigo = null;
        return "acknowledgment";
    }    

    private Boolean existsFormularioForAluno() {
        Formulario formulario = ejbFacade.findByAvaliacaoAndAluno(avaliacaoCodigo, alunoCodigo);
        if (formulario != null) {
            return true;
        }
        return false;
    }

    private Boolean isTodasRespostasPreenchidas() {
        Boolean preenchidas = true;
        for (FormularioResposta resposta : respostas) {
            if (resposta.isQuestaoRespondida() != true) {
                preenchidas = false;
                break;
            }
        }
        return preenchidas;
    }

    private void setRespostasInEntidade() {        
        for (FormularioResposta formularioResposta : respostas) {
            Questao questao = new Questao();

            Resposta resposta = new Resposta();
            questao.setCodigo(formularioResposta.getQuestaoCodigo());
            resposta.setQuestaoCodigo(questao);
            resposta.setResposta(formularioResposta.getStringForResposta());
            resposta.setFormulario(current);
            current.addReposta(resposta);
        }
    }

    public Collection<FormularioResposta> getRespostasForTopico(Integer codigo) {
        Collection<FormularioResposta> respostasTopico = new ArrayList<>();
        for (FormularioResposta resposta : respostas) {
            if (resposta.getTopicoCodigo().equals(codigo) == true) {
                respostasTopico.add(resposta);
            }
        }
        return respostasTopico;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Formulario getFormulario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Collection<Questao> getQuestoesByTopicoCodigo(Integer codigo) {
        List<Integer> codigos;
        codigos = ejbTopicoFacade.findQuestaoByTopico(codigo);
        if (codigos.size() > 0) {
            List<Questao> questoes = ejbQuestaoFacade.findFromList(codigos);
            return questoes;
        }
        return null;
    }

    @FacesConverter(forClass = Formulario.class)
    public static class FormularioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FormularioController controller = (FormularioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "formularioController");
            return controller.getFormulario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Formulario) {
                Formulario o = (Formulario) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Formulario.class.getName());
            }
        }
    }
}
