/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Formulario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author peof
 */
@Stateless
public class FormularioFacade extends AbstractFacade<Formulario> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Formulario findByAvaliacaoAndAluno(Integer avaliacaoCodigo, Integer alunoCodigo) {
        Formulario formulario = null;
        try {
            formulario= (Formulario)em.createNamedQuery("Formulario.findByAvaliacaoAndAluno").setParameter("avaliacaoCodigo", avaliacaoCodigo).setParameter("alunoCodigo", alunoCodigo).getSingleResult();
        } catch (Exception e) {
            if (e.getClass().getName().equals("javax.persistence.NoResultException")) {
                return null;
            } else {
                throw e;
            }            
        }
        return formulario; 
    }    
    

    public FormularioFacade() {
        super(Formulario.class);
    }
    
}
