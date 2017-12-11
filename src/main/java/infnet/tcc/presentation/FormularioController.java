package infnet.tcc.presentation;

import infnet.tcc.entity.Avaliacao;
import infnet.tcc.entity.Formulario;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.FormularioFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;


@Named("formularioController")
@RequestScoped
public class FormularioController implements Serializable {

    private Formulario current;
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.FormularioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    @EJB
    private infnet.tcc.facade.AvaliacaoFacade ejbAvaliacaoFacade;
    private ArrayList<String> comentariosSugestoes;
    private ArrayList<String> respostas;

    public FormularioController() {
        comentariosSugestoes = new ArrayList<>();
        respostas = new ArrayList<>();
    }

    public Formulario getSelected() {
        if (current == null) {
            current = new Formulario();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public ArrayList<String> getRespostas() {
        return respostas;
    }

    public void setRespostas(ArrayList<String> respostas) {
        this.respostas = respostas;
    }
    
    
    public ArrayList<String> getComentariosSugestoes() {
        return comentariosSugestoes;
    }

    public void setComentariosSugestoes(ArrayList<String> comentariosSugestoes) {
        this.comentariosSugestoes = comentariosSugestoes;
    }
    

    @PostConstruct
    public void findAvaliacao() {
        String requestParameter = JsfUtil.getRequestParameter("codigo");
        Integer codigo = null;
        if (requestParameter != null && !requestParameter.isEmpty()) {
            codigo = new Integer(requestParameter);
        }
        if (codigo != null) {
            Avaliacao avaliacao = ejbAvaliacaoFacade.find(codigo);
            getSelected();
            current.setAvaliacao(avaliacao);
        }
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Formulario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Formulario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormularioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Formulario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
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
