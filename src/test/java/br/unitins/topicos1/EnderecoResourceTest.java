package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AuthUsuarioDTO;
import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.EnderecoResponseDTO;
import br.unitins.topicos1.service.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {

        @Inject
        EnderecoService enderecoService;

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
                                .when().get("/enderecos")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                EnderecoDTO endereco = new EnderecoDTO(
                                true,
                                "Praça da Sé",
                                "10",
                                "Loja 5",
                                "Sé",
                                "01001-001",
                                null);

                EnderecoResponseDTO produtoCriado = enderecoService.create(endereco);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(produtoCriado)
                                .when().post("/enderecos")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "principal", is(true),
                                                "logradouro", is("Praça da Sé"),
                                                "numero", is("10"),
                                                "complemento", is("Loja 5"),
                                                "bairro", is("Sé"),
                                                "cep", is("01001-001"),
                                                "idMunicipio", is(null));

        }

        @Test
        public void testUpdate() {
                EnderecoDTO endereco = new EnderecoDTO(
                                true,
                                "Praça da Sé",
                                "10",
                                "Loja 5",
                                "Sé",
                                "01001-001",
                                null);

                Long id = enderecoService.create(endereco).id();

                EnderecoDTO enderecoRequisicao = new EnderecoDTO(
                                true,
                                "Avenida Atlântica",
                                "1500",
                                "Apt 402",
                                "Copacabana",
                                "22020-010",
                                null);

                EnderecoResponseDTO enderecoAtualizado = enderecoService.update(id, enderecoRequisicao);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecoAtualizado)
                                .when().put("/enderecos/" + id)
                                .then()
                                .statusCode(200);

                EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);
                assertThat(enderecoResponse.principal(), is(true));
                assertThat(enderecoResponse.logradouro(), is("Avenida Atlântica"));
                assertThat(enderecoResponse.numero(), is("1500"));
                assertThat(enderecoResponse.complemento(), is("Apt 402"));
                assertThat(enderecoResponse.bairro(), is("Copacabana"));
                assertThat(enderecoResponse.cep(), is("22020-010"));
        }

        @Test
        public void testDelete() {
                EnderecoDTO endereco = new EnderecoDTO(
                                true,
                                "Avenida Atlântica",
                                "1500",
                                "Apt 402",
                                "Copacabana",
                                "22020-010",
                                null);

                Long id = enderecoService.create(endereco).id();

                given()
                                .header("Authorization", "Bearer " + token)
                                .when().delete("/enderecos/" + id)
                                .then()
                                .statusCode(204);

                EnderecoResponseDTO enderecoResponse = null;
                try {
                        enderecoResponse = enderecoService.findById(id);
                } catch (Exception e) {
                        assert true;
                } finally {
                        assertNull(enderecoResponse);
                }
        }
}