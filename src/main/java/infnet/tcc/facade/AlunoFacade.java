/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Aluno;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class AlunoFacade extends AbstractFacade<Aluno> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(Aluno.class);
    }
    
}
