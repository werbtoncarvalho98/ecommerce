package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TelefoneResponseDTO;
import br.unitins.topicos1.service.TelefoneService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        TelefoneDTO telefones = new TelefoneDTO("63", "9 1234-5678");

        given()
                .contentType(ContentType.JSON)
                .body(telefones)
                .when().post("/telefones")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "ddd", is("63"),
                        "numero", is("9 1234-5678"));
    }

    @Test
    public void testUpdate() {
        TelefoneDTO telefones = new TelefoneDTO("63", "9 1234-5678");
        Long id = telefoneService.create(telefones).id();

        TelefoneDTO telefonesUpdate = new TelefoneDTO("99", "9 8765-4321");

        given()
                .contentType(ContentType.JSON)
                .body(telefonesUpdate)
                .when().put("/telefones/" + id)
                .then()
                .statusCode(204);

        TelefoneResponseDTO telefonesResponse = telefoneService.findById(id);
        assertThat(telefonesResponse.ddd(), is("99"));
        assertThat(telefonesResponse.numero(), is("9 8765-4321"));
    }

    @Test
    public void testDelete() {

        TelefoneDTO telefones = new TelefoneDTO("63", "9 1234-5678");
        Long id = telefoneService.create(telefones).id();

        given()
                .when().delete("/telefones/" + id)
                .then()
                .statusCode(204);

        TelefoneResponseDTO telefonesResponse = null;
        try {
            telefonesResponse = telefoneService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(telefonesResponse);
        }
    }
}