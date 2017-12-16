/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Resposta;
import java.util.ArrayList;
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
public class RespostaFacade extends AbstractFacade<Resposta> {

    @PersistenceContext(unitName = "respostaDigitalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespostaFacade() {
        super(Resposta.class);
    }

    public Map<String, List<String>> getReports() {
        Map<String, List<String>> resposta = new HashMap<>();

        try {
            List<Resposta> respostas = em.createNamedQuery("Resposta.getReposts", Resposta.class).getResultList();

            resposta.put("Id", new ArrayList<String>());
            resposta.put("Formulário", new ArrayList<String>());

            for (Resposta resp : respostas) {
                resposta.get("Formulário").add(resp.getFormulario().getCodigo().toString());
                resposta.get("ID").add(resp.getResposta());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta;
    }

}
