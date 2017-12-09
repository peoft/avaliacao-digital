/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Avaliacao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class AvaliacaoFacade extends AbstractFacade<Avaliacao> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public Avaliacao findById(String id) {
        return (Avaliacao) em.createNamedQuery("Avaliacao.findById").setParameter("id", id).getSingleResult();
    }    

    public Avaliacao findByIdDifferentFromCurrent(String id, Integer codigo) {
        return (Avaliacao) em.createNamedQuery("Avaliacao.findByTextoDifferentFromCurrent").setParameter("id", id).setParameter("codigo", codigo).getSingleResult();
    }

    public Avaliacao findByCodigo(Integer codigo) {
        return (Avaliacao) em.createNamedQuery("Avaliacao.findByCodigo").setParameter("codigo", codigo).getSingleResult();
    }    
    
    
    public AvaliacaoFacade() {
        super(Avaliacao.class);
    }
    
}
