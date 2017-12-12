package infnet.tcc.presentation;

import infnet.tcc.entity.Avaliacao;
import infnet.tcc.entity.Questao;
import infnet.tcc.entity.Topico;
import infnet.tcc.entity.Turma;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.AvaliacaoFacade;
import static infnet.tcc.presentation.UserOperations.Create;
import static infnet.tcc.presentation.UserOperations.Update;
import infnet.tcc.presentation.util.DateTimeUtil;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import infnet.tcc.presentation.util.Excell;

@Named("avaliacaoController")
@RequestScoped
public class AvaliacaoController implements Serializable {

    private Avaliacao current;
    private static Avaliacao chosen;
    private Part fileData;
    private String text;
    private String serverUploadImagesPath;
    private String avaliacaoPath;
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.AvaliacaoFacade ejbFacade;
    @EJB
    private infnet.tcc.facade.TopicoFacade ejbTopicoFacade;
    @EJB
    private infnet.tcc.facade.TurmaFacade ejbTurmaFacade;
    @EJB
    private infnet.tcc.facade.QuestaoFacade ejbQuestaoFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Collection<String> titulos;
    private Collection<String> descricoes;
    private Integer codigo;
    private List<String> reports;

    public AvaliacaoController() {
        titulos = new HashSet<>();
        descricoes = new HashSet<>();
        serverUploadImagesPath = ResourceBundle.getBundle("/avaliacaoDigital").getString("upolad-images-path");
    }

    public Avaliacao getSelected() throws IOException {
        if (current == null) {
            current = new Avaliacao();
            selectedItemIndex = -1;
        }

        if (current.getTopicoCollection().size() > 0) {
            setTitulosFromCollection();
        } else {
            if (chosen != null) {
                current.setTopicoCollection(chosen.getTopicoCollection());
            }
        }

        if (current.getAvaliacaoTurmaCollection().size() > 0) {
            setDescricoesFromCollection();
        } else {
            if (chosen != null) {
                current.setAvaliacaoTurmaCollection(chosen.getAvaliacaoTurmaCollection());
            }
        }
        return current;
    }

    private void setTitulosFromCollection() {
        for (Topico topico : current.getTopicoCollection()) {
            getTitulos().add(topico.getTitulo());
        }
    }

    private void setDescricoesFromCollection() {
        for (Turma turma : current.getAvaliacaoTurmaCollection()) {
            getDescricoes().add(turma.getDescricao());
        }
    }

    public Collection<String> getTitulos() {
        return titulos;
    }

    public void setTitulos(Collection<String> titulos) {
        this.titulos = titulos;
    }

    public Collection<String> getDescricoes() {
        return descricoes;
    }

    public void setDescricoes(Collection<String> descricoes) {
        this.descricoes = descricoes;
    }

    public String prepareCreate() {
        current = new Avaliacao();
        chosen = null;
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        chosen = new Avaliacao();
        chosen = current;
        return "Edit";
    }

    public String prepareEditFromView() {
        prepareRequestParameter("codigo");
        chosen = null;
        chosen = new Avaliacao();
        chosen = current;
        return "Edit";
    }

    public String prepareViewFromEdit() {
        prepareRequestParameter("codigo");
        return "View";
    }

    private void prepareRequestParameter(String name) {
        String requestParameter = JsfUtil.getRequestParameter(name);
        if (!requestParameter.isEmpty()) {
            codigo = new Integer(requestParameter);
        } else {
            codigo = chosen.getCodigo();
        }

        current = getFacade().find(codigo);
        selectedItemIndex = -1;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getAvaliacaoPath() {
        return avaliacaoPath;
    }

    public void setAvaliacaoPath(String avaliacaoPath) {
        this.avaliacaoPath = avaliacaoPath;
    }

    private AvaliacaoFacade getFacade() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getServerUploadImagesPath() {
        return serverUploadImagesPath;
    }

    public void setServerUploadImagesPath(String serverUploadImagesPath) {
        this.serverUploadImagesPath = serverUploadImagesPath;
    }

    public Part getFileData() {
        return fileData;
    }

    public void setFileData(Part fileData) {
        this.fileData = fileData;
    }

    public String create() {
        try {
            if (existsIdInDatabase(Create) == false) {
                String logoPath;
                Date currentDate;

                currentDate = DateTimeUtil.getCurrentDate();

                setAvaliacaoPath(getServerUploadImagesPath() + current.getId() + "/");
                logoPath = getLogoPath();
                current.setLogoPath(logoPath);
                processFileUpload();
                current.setCriacao(currentDate);
                current.setModificacao(currentDate);
                addTopicoToCollection();
                addTurmaToCollection();
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvaliacaoCreated"));
                return prepareCreate();
            } else {
                throw new Exception(ResourceBundle.getBundle("/Bundle").getString("ExistsIdAvaliacao"));
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            current.setCodigo(chosen.getCodigo());
            if (existsIdInDatabase(Update) == false) {
                String logoPath;
                Date currentDate;

                currentDate = DateTimeUtil.getCurrentDate();

                setAvaliacaoPath(getServerUploadImagesPath() + current.getId() + "/");
                logoPath = getLogoPath();
                current.setLogoPath(logoPath);
                if (current.getLogoPath().equals(chosen.getLogoPath()) == false) {
                    performRemoveUploadFile(chosen.getLogoPath());
                }
                if (current.getId().equals(chosen.getId()) == false) {
                    performRemoveAvaliacaoPath(getServerUploadImagesPath() + chosen.getId() + "/");
                }

                processFileUpload();
                current.setCriacao(chosen.getCriacao());
                current.setModificacao(currentDate);
                current.getTopicoCollection().clear();
                current.getAvaliacaoTurmaCollection().clear();
                addTopicoToCollection();
                addTurmaToCollection();
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvaliacaoUpdated"));
                chosen = current;
                return "View";
            } else {
                throw new Exception(ResourceBundle.getBundle("/Bundle").getString("ExistsIdAvaliacao"));
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    private boolean existsIdInDatabase(UserOperations operation) {
        boolean exists = true;
        try {
            current.setId(JsfUtil.getStringWithoutExtraWhiteSpaces(current.getId()));
            if (operation == Create) {
                getFacade().findById(current.getId());
            } else if (operation == Update) {
                getFacade().findByIdDifferentFromCurrent(current.getId(), current.getCodigo());
            }
        } catch (EJBException e) {
            Exception cause = (Exception) e.getCause();
            if (cause.getClass().getName().equals("javax.persistence.NoResultException")) {
                exists = false;
            }
        }
        return exists;
    }

    public void processFileUpload() throws IOException {
        InputStream bytes;
        bytes = fileData.getInputStream();
        Files.createDirectories(Paths.get(getAvaliacaoPath()));
        Files.copy(bytes, Paths.get(getLogoPath()), StandardCopyOption.REPLACE_EXISTING);
    }

    private void addTopicoToCollection() {
        for (String titulo : titulos) {
            Topico topico = ejbTopicoFacade.findByTitulo(titulo);
            current.getTopicoCollection().add(topico);
        }
    }

    private void addTurmaToCollection() {
        for (String descricao : descricoes) {
            Turma turma = ejbTurmaFacade.findByDescricao(descricao);
            current.getAvaliacaoTurmaCollection().add(turma);
        }
    }

    private String getLogoPath() {
        String[] data = fileData.toString().split(",");

        data = data[0].split("=");
        return getAvaliacaoPath() + data[1];
    }

    public String destroy() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setAvaliacaoPath(getServerUploadImagesPath() + current.getId() + "/");
        try {
            performRemoveUploadFile(current.getLogoPath());
            performRemoveAvaliacaoPath(getAvaliacaoPath());
            performDestroy();
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RemoveErrorOccurred"));
        }
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        prepareRequestParameter("codigo");
        setAvaliacaoPath(getServerUploadImagesPath() + current.getId() + "/");
        try {
            performRemoveUploadFile(current.getLogoPath());
            performRemoveAvaliacaoPath(getAvaliacaoPath());
            performDestroy();
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RemoveErrorOccurred"));
        }
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvaliacaoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void performRemoveAvaliacaoPath(String avaliacaoPath) throws IOException {
        Files.delete(Paths.get(avaliacaoPath));
    }

    private void performRemoveUploadFile(String logoPath) throws IOException {
        Files.delete(Paths.get(logoPath));
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
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

    private void recreatePagination() {
        pagination = null;
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

    public SelectItem[] getItemsAvailableSelectManyFromTopico() {
        return JsfUtil.getSelectItems(ejbTopicoFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectManyFromTurma() {
        return JsfUtil.getSelectItems(ejbTurmaFacade.findAllByPeriod(DateTimeUtil.getCurrentDate()), false);
    }

    public Avaliacao getAvaliacao(java.lang.Integer codigo) {
        return ejbFacade.find(codigo);
    }

    public Collection<Questao> getQuestoesByTopicoCodigo(Integer codigo) {
        List<Integer> codigos;
        codigos = ejbTopicoFacade.findQuestaoByTopico(codigo);
        if (codigos.size() > 0) {
            return ejbQuestaoFacade.findFromList(codigos);
        }
        return null;
    }

    @FacesConverter(forClass = Avaliacao.class)
    public static class AvaliacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AvaliacaoController controller = (AvaliacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "avaliacaoController");
            return controller.getAvaliacao(getKey(value));
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
            if (object instanceof Avaliacao) {
                Avaliacao o = (Avaliacao) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Avaliacao.class.getName());
            }
        }

    }

    public void getReports() {

        try {
            Excell.getReport(ejbFacade.getReports(), "Avaliações");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
