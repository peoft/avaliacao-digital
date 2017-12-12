/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Avaliacao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String, List<String>> getReports() {
        Map<String, List<String>> avaliacao = new HashMap<>();

        try {
            List<Avaliacao> avaliacoes = em.createNamedQuery("Avaliacao.getReposts", Avaliacao.class).getResultList();

            
            avaliacao.put("Id", new ArrayList<String>());
            avaliacao.put("Código", new ArrayList<String>());
            avaliacao.put("Objetivo", new ArrayList<String>());
            avaliacao.put("Data Inicial", new ArrayList<String>());
            avaliacao.put("Data Termino", new ArrayList<String>());
            avaliacao.put("Descrição", new ArrayList<String>());
            avaliacao.put("Link de paginas", new ArrayList<String>());
            
            for (Avaliacao aval : avaliacoes) {
                avaliacao.get("Id").add(aval.getId());
                avaliacao.get("Código").add(aval.getCodigo().toString());
                avaliacao.get("Objetivo").add(aval.getObjetivo());
                avaliacao.get("Data Inicial").add(aval.getInicio().toString());
                avaliacao.get("Data Termino").add(aval.getTermino().toString());
                avaliacao.get("Descrição").add(aval.getTextoConvidativo());
                avaliacao.get("Link de paginas").add(aval.getLinkPagina());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avaliacao;
    }

}
