/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.service;

import infnet.tcc.entity.Turma;
import infnet.tcc.entity.TurmaPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author peof
 */
@Stateless
@Path("/turma/")
public class TurmaFacadeREST extends AbstractFacade<Turma> {

    @PersistenceContext(unitName = "avaliacaoDigitalPU")
    private EntityManager em;

    private TurmaPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codigo=codigoValue;inicio=inicioValue;fim=fimValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        infnet.tcc.entity.TurmaPK key = new infnet.tcc.entity.TurmaPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codigo = map.get("codigo");
        if (codigo != null && !codigo.isEmpty()) {
            key.setCodigo(new java.lang.Integer(codigo.get(0)));
        }
        java.util.List<String> inicio = map.get("inicio");
        if (inicio != null && !inicio.isEmpty()) {
            key.setInicio(new java.util.Date(inicio.get(0)));
        }
        java.util.List<String> fim = map.get("fim");
        if (fim != null && !fim.isEmpty()) {
            key.setFim(new java.util.Date(fim.get(0)));
        }
        return key;
    }

    public TurmaFacadeREST() {
        super(Turma.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Turma entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Turma entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        infnet.tcc.entity.TurmaPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Turma find(@PathParam("id") PathSegment id) {
        infnet.tcc.entity.TurmaPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Turma> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Turma> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
