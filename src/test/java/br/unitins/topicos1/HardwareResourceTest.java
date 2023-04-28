package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;
import br.unitins.topicos1.service.HardwareService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class HardwareResourceTest {

        @Inject
        HardwareService hardwareService;

        @Test
        public void getAllTest() {
                given()
                                .when().get("/hardwares")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                // Cria um objeto Fabricante
                FabricanteDTO fabricante = new FabricanteDTO("Gigabyte", "gigabyte.com");

                Date lancamento = new Date();

                // Insere o Fabricante no banco de dados e obtém o seu id
                Long idFabricante = given()
                                .contentType(ContentType.JSON)
                                .body(fabricante)
                                .when().post("/fabricantes")
                                .then()
                                .statusCode(201)
                                .extract()
                                .path("id");

                HardwareDTO hardware = new HardwareDTO(
                                "RTX 2060",
                                lancamento,
                                1,
                                1,
                                idFabricante.intValue());

                given()
                                .contentType(ContentType.JSON)
                                .body(hardware)
                                .when().post("/hardwares")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "modelo", is("RTX 2060"),
                                                "lancamento", is(lancamento.getTime()),
                                                "nivel", is(1),
                                                "integridade", is(1),
                                                "fabricante", is(idFabricante.intValue()));
        }

        @Test
        public void testUpdate() {
                HardwareDTO hardware = new HardwareDTO("RTX 2060", null, 3, 1, null);
                Long id = hardwareService.create(hardware).id();

                HardwareDTO hardwareUpdate = new HardwareDTO("RX 6600 XT", null, 3, 1, null);

                given()
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
}