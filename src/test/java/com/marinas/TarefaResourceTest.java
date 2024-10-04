package com.marinas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.net.URL;

import com.marinas.managertask.resorce.TarefaResorce;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TarefaResourceTest {
	@TestHTTPEndpoint(TarefaResorce.class)
	@TestHTTPResource
    URL url;	
	
	@Test
	public void testNewTask() {	
		String json = "{\"nome\":\"Criar teste create task\",\"dia\":\"2024-10-28\",\"status\":\"TO_DO\"}";

		given()
        	.contentType(ContentType.JSON)
        	.body(json)
        .when()
        	.post("/tarefas")
        .then()
        	.statusCode(201)
        	.body("nome", equalTo("Criar teste create task"))
        	.body("status", equalTo("TO_DO"));
	} 
	
	
	@Test
	public void getAllTest() {
		given()
			.when()
				.get("/tarefas")
			.then()
				.statusCode(201)
				.contentType(ContentType.JSON)
                .body("size()", is(greaterThan(0))) // Verifica que a lista tem ao menos um item
                .body("[0].nome", notNullValue()) // Verifica que o primeiro item tem um nome
                .body("[0].status", notNullValue());
	}
	
	@Test
	public void testFindByNome() {
        String nomeBuscado = "fazer endpoint create";

        given()
            	.queryParam("nome", nomeBuscado) // Passa o parâmetro de consulta 'nome'
            .when()
            	.get("/tarefas/findByNome") // Faz a requisição GET ao endpoint com o parâmetro 'nome'
            .then()
	            .statusCode(200) // Verifica se o status de retorno é 200 (OK)
	            .contentType(ContentType.JSON) // Verifica se o retorno está no formato JSON
	            .body("size()", greaterThan(0)) // Verifica se a lista retornada contém ao menos um item
	            .body("[0].nome", equalTo(nomeBuscado)); // Verifica que o nome do primeiro item da lista corresponde ao nome buscado
    }
	
	@Test
	public void testFindByDay() {
		 String diaBuscado = "2024-10-28"; // A data deve ser passada como string no formato ISO (YYYY-MM-DD)

		 given()
		 		.queryParam("dia", diaBuscado) // Passa o parâmetro de consulta 'dia' como string
	        .when()
	            .get("/tarefas/findByDay") // Faz a requisição GET ao endpoint com o parâmetro 'dia'
	        .then()
	            .statusCode(200) // Verifica se o status de retorno é 200 (OK)
	            .contentType(ContentType.JSON) // Verifica se o retorno está no formato JSON
	            .body("size()", greaterThan(0)) // Verifica se a lista retornada contém ao menos um item
	            .body("[0].dia", equalTo(diaBuscado)); // Verifica se o 'dia' do primeiro item corresponde ao valor buscado
	    }
	 
	@Test
    public void testFindByNomeLike() {
		String nomeBuscado = "excluir"; // Parte do nome que será usada para a busca "like"

        given()
            	.queryParam("nome", nomeBuscado) // Passa o parâmetro de consulta 'nome'
        .when()
            .get("/tarefas/findByNomeLike") // Faz a requisição GET ao endpoint com o parâmetro 'nome'
        .then()
            .statusCode(200) // Verifica se o status de retorno é 200 (OK)
            .contentType(ContentType.JSON) // Verifica se o retorno está no formato JSON
            .body("size()", greaterThan(0)) // Verifica se a lista retornada contém ao menos um item
            .body("[0].nome", containsString(nomeBuscado)); // Verifica que o nome do primeiro item contém a string buscada
    }
	
	@Test
    public void testDeleteById() {
        long idParaDeletar = 701; // ID da tarefa a ser deletada (você pode ajustar conforme seu cenário)

        given()
            .queryParam("id", idParaDeletar) // Passa o parâmetro de consulta 'id'
        .when()
            .delete("/tarefas/deleteById") // Faz a requisição DELETE ao endpoint com o parâmetro 'id'
        .then()
            .statusCode(204); // Verifica se o status de retorno é 204 (No Content)
    }
	
	@Test
    public void testUpdateTask() {
        Long idParaAtualizar = 1L; // ID da tarefa que será atualizada

        // JSON representando a nova tarefa
        String jsonAtualizado = "{\"nome\":\"Tarefa Atualizada\",\"dia\":\"2024-10-30\",\"status\":\"DONE\"}";

        given()
            .contentType(ContentType.JSON) // Define o tipo de conteúdo como JSON
            .body(jsonAtualizado) // Passa o corpo da requisição com os dados da tarefa atualizada
        .when()
            .put("/tarefas/" + idParaAtualizar) // Faz a requisição PUT ao endpoint com o ID na URL
        .then()
            .statusCode(202) // Verifica se o status de retorno é 202 (Accepted)
            .body("nome", equalTo("Tarefa Atualizada")) // Verifica se o nome retornado é o esperado
            .body("status", equalTo("DONE")); // Verifica se o status retornado é o esperado
    }

    @Test
    public void testUpdateTaskNotFound() {
        Long idInexistente = 999L; // ID de uma tarefa que não existe

        // JSON representando a nova tarefa
        String jsonAtualizado = "{\"nome\":\"Tarefa Inexistente\",\"dia\":\"2024-10-30\",\"status\":\"DONE\"}";

        given()
            .contentType(ContentType.JSON) // Define o tipo de conteúdo como JSON
            .body(jsonAtualizado) // Passa o corpo da requisição com os dados da tarefa atualizada
        .when()
            .put("/tarefas/" + idInexistente) // Faz a requisição PUT ao endpoint com um ID que não existe
        .then()
            .statusCode(500); // Verifica se o status de retorno é 404 (Not Found)
    }

}
