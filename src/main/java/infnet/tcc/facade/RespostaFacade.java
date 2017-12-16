/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.facade;

import infnet.tcc.entity.Resposta;
import infnet.tcc.entity.Turma;
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

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
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
            List<Resposta> respostas = em.createNamedQuery("Resposta.getReports", Resposta.class).getResultList();

            resposta.put("Formulário ID", new ArrayList<String>());
            resposta.put("Módulo", new ArrayList<String>());
            resposta.put("Aluno", new ArrayList<String>());
            resposta.put("CPF", new ArrayList<String>());
            resposta.put("Pergunta", new ArrayList<String>());
            resposta.put("Resposta", new ArrayList<String>());
            resposta.put("Sugestões", new ArrayList<String>());
            resposta.put("Professor", new ArrayList<String>());

            for (Resposta resp : respostas) {

                String professor = "";
                for (Turma turma : resp.getFormulario().getAvaliacao().getAvaliacaoTurmaCollection()) {

                    professor = turma.getProfessorCodigo().getNome();
                }
                resposta.get("Formulário ID").add(resp.getFormulario().getCodigo().toString());
                resposta.get("Módulo").add(resp.getFormulario().getNomeModulo());
                resposta.get("Aluno").add(resp.getFormulario().getNomeAluno());
                resposta.get("CPF").add(resp.getFormulario().getCpfAluno().toString());
                resposta.get("Resposta").add(resp.getResposta());
                resposta.get("Pergunta").add(resp.getQuestao().getTexto());
                resposta.get("Sugestões").add(resp.getFormulario().getComentariosSugestoes());
                resposta.get("Professor").add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta;
    }

}
