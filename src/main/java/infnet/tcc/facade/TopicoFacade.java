/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Topico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class TopicoFacade extends AbstractFacade<Topico> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Topico findByTitulo(String titulo) {
        return (Topico) em.createNamedQuery("Topico.findByTitulo").setParameter("titulo", titulo).getSingleResult();
    }
    
    public TopicoFacade() {
        super(Topico.class);
    }
    
}
