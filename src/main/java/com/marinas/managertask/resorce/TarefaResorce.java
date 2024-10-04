package com.marinas.managertask.resorce;

import java.time.LocalDate;
import java.util.List;

import com.marinas.managertask.StatusTask;
import com.marinas.managertask.Tarefa;
import com.marinas.managertask.repository.TarefaRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/tarefas")
public class TarefaResorce {
	 @Inject
	 TarefaRepository tarefaRepository;
	 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(){
		//return Tarefa.listAll();
		List<Tarefa> tarefas = Tarefa.listAll();
		
		return Response.ok(tarefas).status(201).build();
	}
	
	@Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTask(Tarefa tarefa){
		tarefa.id = null;
		tarefa.status = StatusTask.TO_DO;
		
        tarefa.persist();

        return Response.status(Status.CREATED).entity(tarefa).build();
    }
	
	//localhost:8080/tarefa/findByNome?nome=xxxx
    @GET
    @Path("findByNome")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tarefa> findByNome(@QueryParam("nome") String nome){
        return tarefaRepository.findByNome(nome);
    }
    
    //localhost:8080/tarefa/findByDay?nome=xxxx
    @GET
    @Path("findByDay")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tarefa> findByDay(@QueryParam("dia") LocalDate dia){
        return tarefaRepository.findByDia(dia);
    }
    
    //localhost:8080/tarefa/findByNomeLike?nome=xxxx
    @GET
    @Path("findByNomeLike")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tarefa> findByNomeLike(@QueryParam("nome") String nome){
        return tarefaRepository.findByNomeLike(nome);
    }
    
    @Transactional
    @DELETE
    @Path("deleteById")
    public Response deleteById(@QueryParam("id") long id){
        Tarefa.deleteById(id);
        return Response.noContent().build();
    }
    
    @Transactional
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTask(@PathParam("id") Long id, Tarefa tarefa){
    	Tarefa tarefaCadastrada = tarefaRepository.findByIdOptional(id)
    								.orElseThrow(() -> new NotFoundException("Tarefa com id " + id + " nao encontrada"));
    	
		tarefaCadastrada.nome = tarefa.nome;
		tarefaCadastrada.dia = tarefa.dia;
		tarefaCadastrada.status = tarefa.status;
		
	    return Response.status(Status.ACCEPTED).entity(tarefaCadastrada).build();
	}
    

}
