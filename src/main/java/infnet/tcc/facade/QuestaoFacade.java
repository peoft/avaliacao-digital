/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Questao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class QuestaoFacade extends AbstractFacade<Questao> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Questao findByTexto(String texto) {
        return (Questao) em.createNamedQuery("Questao.findByTexto").setParameter("texto", texto).getSingleResult();
    }    

    public Questao findByTextoDifferentFromCurrent(String texto, Integer codigo) {
        return (Questao) em.createNamedQuery("Questao.findByTextoDifferentFromCurrent").setParameter("texto", texto).setParameter("codigo", codigo).getSingleResult();
    }    
    
    public List<Questao> findFromList(List<Integer> codigos) {
        return (List<Questao>) em.createNamedQuery("Questao.findFromList").setParameter("codigos", codigos).getResultList();
    }
    
    public QuestaoFacade() {
        super(Questao.class);
    }
    
}
