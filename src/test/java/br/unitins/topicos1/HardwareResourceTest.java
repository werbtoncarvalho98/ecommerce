package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AuthUsuarioDTO;
import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;
import br.unitins.topicos1.model.Fabricante;
import br.unitins.topicos1.service.FabricanteService;
import br.unitins.topicos1.service.HardwareService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
public class HardwareResourceTest {

        @Inject
        HardwareService hardwareService;

        @Inject
        FabricanteService fabricanteService;

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
                                .when().get("/hardwares")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                Long id = fabricanteService.create(new FabricanteDTO("Nvidia", "nvidia.com")).id();

                HardwareDTO hardware = new HardwareDTO("RTX 2060", null, 1, 1, id);

                FabricanteDTO fabricanteDTO = new FabricanteDTO("Nvidia", "nvidia.com");

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(hardware)
                                .when().post("/hardwares")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "modelo", is("RTX 2060"),
                                                "lancamento", is(null),
                                                "nivel", is(1),
                                                "integridade", is(1),
                                                "fabricante", notNullValue());
        }

        @Test
        public void testUpdate() {
                HardwareDTO hardware = new HardwareDTO("RTX 2060", null, 3, 1, null);
                Long id = hardwareService.create(hardware).id();

                HardwareDTO hardwareUpdate = new HardwareDTO("RX 6600 XT", null, 3, 1, null);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(hardwareUpdate)
                                .when().put("/hardwares/" + id)
                                .then()
                                .statusCode(204);

                HardwareResponseDTO hardwareResponse = hardwareService.findById(id);
                assertThat(hardwareResponse.modelo(), is("RX 6600 XT"));
                assertThat(hardwareResponse.lancamento(), is(null));
                assertThat(hardwareResponse.nivel(), is(3));
                assertThat(hardwareResponse.integridade(), is(3));
                assertThat(hardwareResponse.fabricante(), is(null));
        }

        @Test
        public void testDelete() {
                HardwareDTO hardware = new HardwareDTO("RTX 2060", null, 1, 1, null);
                Long id = hardwareService.create(hardware).id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().delete("/hardwares/" + id)
                                .then()
                                .statusCode(204);

                HardwareResponseDTO hardwareResponse = null;
                try {
                        hardwareResponse = hardwareService.findById(id);
                } catch (Exception e) {

                } finally {
                        assertNull(hardwareResponse);
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