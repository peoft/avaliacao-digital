/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infnet.tcc.presentation;
import infnet.tcc.entity.Usuario;
import infnet.tcc.presentation.util.JsfUtil;
import infnet.tcc.presentation.util.PaginationHelper;
import infnet.tcc.facade.UsuarioFacade;

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
import javax.servlet.http.HttpSession;

@Named("loginController")
@SessionScoped
/**
 *
 * @author Leonardo
 */
public class LoginController implements Serializable{
    
    
    private Usuario current;
   
    @EJB
    private infnet.tcc.facade.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;  
    
    private String senha;
    private String login;
    
    public LoginController() {
    }
    
    
    public String Login()
    {
        try{
        String login = getLogin();
        String senha = getSenha();
        
        current = (Usuario) ejbFacade.findByLoginUsuario(login,senha);
              if(current != null)                  
                   return "/faces/index.xhtml";
              else
                  return "/faces/login.xhtml";
       
        }
        catch(Exception e)
        {
            return null;
        }    
    
    
    }
    
    public String logout() {
        try{
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessao.invalidate();
        return "/faces/login.xhtml";
      }
        catch(Exception e){
        
        e.getMessage();
        }
            return null;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    
}
