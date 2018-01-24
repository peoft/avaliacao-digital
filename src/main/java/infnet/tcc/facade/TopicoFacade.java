/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Topico;
import java.util.List;
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
    
    public Topico findByTituloDifferentFromCurrent(String titulo, Integer codigo) {
        return (Topico) em.createNamedQuery("Topico.findByTituloDifferentFromCurrent").setParameter("titulo", titulo).setParameter("codigo", codigo).getSingleResult();
    }
    
    public List<Integer> findQuestaoByTopico(Integer codigo) {
        return (List<Integer>) em.createNamedQuery("Topico.findQuestaoByTopico").setParameter("codigo", codigo).getResultList();
    }
    
    
    public TopicoFacade() {
        super(Topico.class);
    }
    
}
