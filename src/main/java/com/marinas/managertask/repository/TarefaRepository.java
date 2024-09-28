package com.marinas.managertask.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.marinas.managertask.Tarefa;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TarefaRepository implements PanacheRepository<Tarefa> {
	
	public List<Tarefa> findByNome(String nome) {
        return find("nome", nome).list();
    }
	
	public List<Tarefa> findByNomeLike(String nome) {
        // Usando JPQL para consulta com LIKE
        return find("nome like ?1", "%" + nome + "%").list();
    }
	
	public Optional<Tarefa> buscarTarefaPorId(Long id) {
	    return Optional.ofNullable(Tarefa.findById(id));
	}
	
	public List<Tarefa> findByDia(LocalDate dia) {
        return find("dia", dia).list();
    }
}
