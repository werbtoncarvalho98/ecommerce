package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AuthUsuarioDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TelefoneResponseDTO;
import br.unitins.topicos1.service.TelefoneService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneService;

    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("joao", "abc123");

        Response response = (Response) given()
                .contentType("application/json")
                .body(auth)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        token = response.header("Authorization");
    }

    @Test
    public void getAllTest() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        TelefoneDTO telefones = new TelefoneDTO("63", "9 1234-5678");

        given()
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
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

    @Test
    public void testFindById() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/estados/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/estados/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/estados/search/tocantins")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}