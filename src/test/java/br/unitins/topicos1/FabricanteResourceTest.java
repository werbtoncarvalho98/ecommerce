package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.FabricanteResponseDTO;
import br.unitins.topicos1.service.FabricanteService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class FabricanteResourceTest {

    @Inject
    FabricanteService fabricanteService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/fabricantes")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        FabricanteDTO fabricantes = new FabricanteDTO("Nvidia", "nvidia.com");

        given()
                .contentType(ContentType.JSON)
                .body(fabricantes)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Nvidia"),
                        "website", is("nvidia.com"));
    }

    @Test
    public void testUpdate() {
        FabricanteDTO fabricantes = new FabricanteDTO("Nvidia", "nvidia.com");
        Long id = fabricanteService.create(fabricantes).id();

        FabricanteDTO fabricantesUpdate = new FabricanteDTO("Gigabyte", "gigabyte.com");

        given()
                .contentType(ContentType.JSON)
                .body(fabricantesUpdate)
                .when().put("/fabricantes/" + id)
                .then()
                .statusCode(204);

        FabricanteResponseDTO fabricantesResponse = fabricanteService.findById(id);
        assertThat(fabricantesResponse.nome(), is("Gigabyte"));
        assertThat(fabricantesResponse.website(), is("gigabyte.com"));
    }

    @Test
    public void testDelete() {
        FabricanteDTO fabricante = new FabricanteDTO("Nvidia", "nvidia.com");
        Long id = fabricanteService.create(fabricante).id();

        given()
                .when().delete("/fabricantes/" + id)
                .then()
                .statusCode(204);

        FabricanteResponseDTO fabricantesResponse = null;
        try {
            fabricantesResponse = fabricanteService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(fabricantesResponse);
        }
    }
}