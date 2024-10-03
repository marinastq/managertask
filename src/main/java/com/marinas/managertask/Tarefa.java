package com.marinas.managertask;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Tarefa extends PanacheEntity{
	//panache gerencia ID no PanacheEntity
	@Column(nullable = false)
	public String nome;
	
	@Column(nullable = false)
	@JsonbDateFormat("yyyy-MM-dd")
	public LocalDate dia;
	
	@Enumerated(EnumType.STRING) // Armazena o enum como string no banco de dados
    public StatusTask status;

	public Tarefa(String nome, LocalDate dia, StatusTask status) {
		super();
		this.nome = nome;
		this.dia = dia;
		this.status = status;
	}
	
	public Tarefa() {
		
	}
	
}
