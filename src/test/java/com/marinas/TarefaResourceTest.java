package com.marinas;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.marinas.managertask.StatusTask;
import com.marinas.managertask.Tarefa;
import com.marinas.managertask.resorce.TarefaResorce;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.net.URL;

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

}
