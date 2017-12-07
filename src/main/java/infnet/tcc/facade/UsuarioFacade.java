/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;
import infnet.tcc.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;
   

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }    
    
     public Usuario findByLoginUsuario(String login, String senha) {
         Usuario usuario;
         usuario = (Usuario) em.createNamedQuery("Usuario.findByLoginUsuario").setParameter("login", login).setParameter("senha", senha).getSingleResult();
        
         return usuario;
    }
    
}
