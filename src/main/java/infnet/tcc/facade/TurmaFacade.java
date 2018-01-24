/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Turma;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class TurmaFacade extends AbstractFacade<Turma> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Turma> findAllByPeriod(Date period) {
        List<Turma> turmas;
        turmas = (List<Turma>) em.createNamedQuery("Turma.findAllByPeriod").setParameter("period", period).getResultList();
        return turmas;
    }    
    
    public Turma findByDescricao(String descricao) {
        return (Turma) em.createNamedQuery("Turma.findByDescricao").setParameter("descricao", descricao).getSingleResult();
    }

    public TurmaFacade() {
        super(Turma.class);
    }
    
}
