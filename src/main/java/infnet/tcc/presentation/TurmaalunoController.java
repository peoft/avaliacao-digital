package infnet.tcc.presentation;

import infnet.tcc.entity.Turmaaluno;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.TurmaalunoFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("turmaalunoController")
@SessionScoped
public class TurmaalunoController implements Serializable {

    private Turmaaluno current;
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.TurmaalunoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TurmaalunoController() {
    }

    public Turmaaluno getSelected() {
        if (current == null) {
            current = new Turmaaluno();
            current.setTurmaalunoPK(new infnet.tcc.entity.TurmaalunoPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private TurmaalunoFacade getFacade() {
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
        current = (Turmaaluno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Turmaaluno();
        current.setTurmaalunoPK(new infnet.tcc.entity.TurmaalunoPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getTurmaalunoPK().setAlunoCPF(current.getAluno().getCpf());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TurmaalunoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Turmaaluno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getTurmaalunoPK().setAlunoCPF(current.getAluno().getCpf());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TurmaalunoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Turmaaluno) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TurmaalunoDeleted"));
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

    public Turmaaluno getTurmaaluno(infnet.tcc.entity.TurmaalunoPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Turmaaluno.class)
    public static class TurmaalunoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TurmaalunoController controller = (TurmaalunoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "turmaalunoController");
            return controller.getTurmaaluno(getKey(value));
        }

        infnet.tcc.entity.TurmaalunoPK getKey(String value) {
            infnet.tcc.entity.TurmaalunoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new infnet.tcc.entity.TurmaalunoPK();
            key.setAlunoCPF(Long.parseLong(values[0]));
            key.setTurmaCodigo(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(infnet.tcc.entity.TurmaalunoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getAlunoCPF());
            sb.append(SEPARATOR);
            sb.append(value.getTurmaCodigo());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Turmaaluno) {
                Turmaaluno o = (Turmaaluno) object;
                return getStringKey(o.getTurmaalunoPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Turmaaluno.class.getName());
            }
        }

    }

}
