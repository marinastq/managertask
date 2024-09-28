package com.marinas.managertask;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Tarefa extends PanacheEntity{
	//panache gerencia ID no PanacheEntity
	public String nome;
}
