package infnet.tcc.presentation;

import infnet.tcc.entity.Avaliacao;
import infnet.tcc.entity.Topico;
import infnet.tcc.entity.Turma;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.AvaliacaoFacade;
import static infnet.tcc.presentation.UserOperations.Create;
import static infnet.tcc.presentation.UserOperations.Update;
import infnet.tcc.presentation.util.DateTimeUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

@Named("avaliacaoController")
@RequestScoped
public class AvaliacaoController implements Serializable {

    private Avaliacao current;
    private String fileData;
    private final String serverLogoPath = "www/avaliacaoDigital/images/";
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.AvaliacaoFacade ejbFacade;
    @EJB
    private infnet.tcc.facade.TopicoFacade ejbTopicoFacade;
    @EJB
    private infnet.tcc.facade.TurmaFacade ejbTurmaFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Collection<String> titulos;
    private Collection<String> descricoes;
    
    public AvaliacaoController() {
        titulos = new HashSet<String>();
        descricoes = new HashSet<String>();
    }

    public Avaliacao getSelected() {
        if (current == null) {
            current = new Avaliacao();
            selectedItemIndex = -1;
        }
        return current;
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

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String create() {
        try {
            if (existsIdInDatabase(Create) == false) {
                String logoPath;                
                Date currentDate;
                
                currentDate = DateTimeUtil.getCurrentDate();                
                logoPath = getLogoPath();
                current.setLogoPath(logoPath);
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

    private void addTopicoToCollection() {
        for (String titulo : titulos) {
            Topico topico = ejbTopicoFacade.findByTitulo(titulo);
            current.getTopicoCollection().add(topico);
        }
    }
    
    private void addTurmaToCollection() {
        for (String descricao : descricoes) {
            Turma turma = ejbTurmaFacade.findByDescricao(descricao);
            current.getAvalicaoTurmaCollection().add(turma);
        }        
    }

    private String getLogoPath() {
        String[] data = fileData.split(",");

        data = data[0].split("=");
        return serverLogoPath + data[1];
    }

    public String prepareCreate() {
        current = new Avaliacao();
        selectedItemIndex = -1;
        return "Create";
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

    public String prepareEdit() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvaliacaoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Avaliacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
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

}
