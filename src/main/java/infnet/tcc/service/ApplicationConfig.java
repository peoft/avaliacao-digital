/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author peof
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(infnet.tcc.service.AdministradorFacadeREST.class);
        resources.add(infnet.tcc.service.AlunoFacadeREST.class);
        resources.add(infnet.tcc.service.AvaliacaoFacadeREST.class);
        resources.add(infnet.tcc.service.AvaliacaotopicoFacadeREST.class);
        resources.add(infnet.tcc.service.FormularioFacadeREST.class);
        resources.add(infnet.tcc.service.ModuloFacadeREST.class);
        resources.add(infnet.tcc.service.ProfessorFacadeREST.class);
        resources.add(infnet.tcc.service.QuestaoFacadeREST.class);
        resources.add(infnet.tcc.service.RespostaFacadeREST.class);
        resources.add(infnet.tcc.service.TopicoFacadeREST.class);
        resources.add(infnet.tcc.service.TopicoquestaoFacadeREST.class);
        resources.add(infnet.tcc.service.TurmaFacadeREST.class);
        resources.add(infnet.tcc.service.TurmaalunoFacadeREST.class);
        resources.add(infnet.tcc.service.UsuarioFacadeREST.class);
    }
    
}
