package infnet.tcc.presentation;

import infnet.tcc.entity.Questao;
import infnet.tcc.entity.Topico;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.QuestaoFacade;
import infnet.tcc.facade.TopicoFacade;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

@Named("questaoController")
@SessionScoped
public class QuestaoController implements Serializable {

    @EJB
    private TopicoFacade topicoFacade;

    private Questao current;
    private DataModel items = null;
    @EJB
    private infnet.tcc.facade.QuestaoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Topico topico;

    public QuestaoController() {
    }

    public Questao getSelected() {
        if (current == null) {
            current = new Questao();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Topico getSelectedTopico() {
        if (topico == null) {
            topico = new Topico();
            selectedItemIndex = -1;
        }
        return topico;
    }

    private QuestaoFacade getFacade() {
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
        current = (Questao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Questao();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareEdit() {
        current = (Questao) getItems().getRowData();
        Iterator<Topico> iterator = current.getTopicoCollection().iterator();
        if (iterator.hasNext()) {
            topico = (Topico) current.getTopicoCollection().iterator().next();
        }
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    private void setTopicoCodigoFromTitulo() {
        TopicoController topicoController = new TopicoController();
        topicoController.setFacade(topicoFacade);

        Topico topicoTitulo = topicoController.getTopicoByTitulo(topico.getTitulo());
        topico.setCodigo(topicoTitulo.getCodigo());
    }

    private boolean existsTextInDatabase() {
        boolean exists = true;
        try {
            Questao questao = getFacade().findByTexto(current.getTexto().trim().replaceAll("\\s+", " "));
        } catch (EJBException e) {
            Exception cause = (Exception) e.getCause();
            if (cause.getClass().getName().equals("javax.persistence.NoResultException")) {
                exists = false;
            }
        }
        return exists;
    }
    
    private Date getCurrentDate() {
        Instant instant = Instant.now();
        Locale locale = new Locale("pt", "BR");
        ZoneId zoneId = ZoneId.of( "America/Sao_Paulo" );
        ZonedDateTime zonedDT = instant.atZone( zoneId );
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                                       .withLocale( locale );
        
        zonedDT.format(formatter);
        
        Calendar calendar ;
        calendar = GregorianCalendar.from( zonedDT );
        
        return  calendar.getTime();
    }
    
    public String create() {
        try {
            if (existsTextInDatabase() == false) {
                Date currentDate = getCurrentDate();

                current.setCriacao(currentDate);
                current.setModificacao(currentDate);
                setTopicoCodigoFromTitulo();
                current.getTopicoCollection().add(topico);
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestaoCreated"));
                return prepareCreate();
            } else {
                throw new Exception(ResourceBundle.getBundle("/Bundle").getString("ExistsTextoQuestao"));
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String update() {
        try {
            Date currentDate = getCurrentDate();
            setTopicoCodigoFromTitulo();
            current.setModificacao(currentDate);
            current.getTopicoCollection().clear();
            current.getTopicoCollection().add(topico);
            getFacade().edit(current);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestaoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Questao) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestaoDeleted"));
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

    public Questao getQuestao(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Questao.class)
    public static class QuestaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoController controller = (QuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoController");
            return controller.getQuestao(getKey(value));
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
            if (object instanceof Questao) {
                Questao o = (Questao) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Questao.class.getName());
            }
        }

    }

}
