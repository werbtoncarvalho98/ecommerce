package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.EstadoDTO;
import br.unitins.topicos1.dto.EstadoResponseDTO;
import br.unitins.topicos1.service.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    EstadoService estadoService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        EstadoDTO estados = new EstadoDTO("Tocantins", "TO");

        given()
                .contentType(ContentType.JSON)
                .body(estados)
                .when().post("/estados")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Tocantins"),
                        "sigla", is("TO"));
    }

    @Test
    public void testUpdate() {
        EstadoDTO estados = new EstadoDTO("Tocantins", "TO");
        Long id = estadoService.create(estados).id();

        EstadoDTO estadosUpdate = new EstadoDTO("Maranhao", "MA");

        given()
                .contentType(ContentType.JSON)
                .body(estadosUpdate)
                .when().put("/estados/" + id)
                .then()
                .statusCode(204);

        EstadoResponseDTO estadosResponse = estadoService.findById(id);
        assertThat(estadosResponse.nome(), is("Maranhao"));
        assertThat(estadosResponse.sigla(), is("MA"));
    }

    @Test
    public void testDelete() {
        EstadoDTO estados = new EstadoDTO("Tocantins", "TO");
        Long id = estadoService.create(estados).id();

        given()
                .when().delete("/estados/" + id)
                .then()
                .statusCode(204);

        EstadoResponseDTO estadosResponse = null;
        try {
            estadosResponse = estadoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(estadosResponse);
        }
    }
}