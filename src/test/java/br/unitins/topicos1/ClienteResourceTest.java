package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ClienteResourceTest {

        @Inject
        ClienteService usuarioService;

        @Test
        public void getAllTest() {
                given()
                                .when().get("/clientes")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                var telefones = new ArrayList<TelefoneDTO>();
                telefones.add(new TelefoneDTO("99", "9 1234-5678"));

                var enderecos = new ArrayList<EnderecoDTO>();
                enderecos.add(new EnderecoDTO(true,
                                "Avenida Paulista",
                                "1234",
                                "Sala 567",
                                "Bela Vista",
                                "01310-100",
                                null));

                ClienteDTO usuario = new ClienteDTO("João da Silva Santos",
                                "joao.silva.santos@gmail.com",
                                "123.456.789-00");

                ClienteResponseDTO usuarioCriado = usuarioService.create(usuario);

                given()
                                .contentType(ContentType.JSON)
                                .body(usuarioCriado)
                                .when().post("/clientes")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "nome", is("Joyce Aguiar"),
                                                "email", is("joyce@gmail.com"),
                                                "senha", is("095684848"),
                                                "cpf", is("08734567867"));

        }

        @Test
        public void testUpdate() {
                var telefones = new ArrayList<TelefoneDTO>();
                telefones.add(new TelefoneDTO("63", "9985438645"));

                var enderecos = new ArrayList<EnderecoDTO>();
                enderecos.add(new EnderecoDTO(true,
                                "Avenida Paulista",
                                "1234",
                                "Sala 567",
                                "Bela Vista",
                                "01310-100",
                                null));

                ClienteDTO usuario = new ClienteDTO("João da Silva Santos",
                                "joao.silva.santos@gmail.com",
                                "123.456.789-00");

                Long id = usuarioService.create(usuario).id();

                ClienteDTO usuarioRequisicao = new ClienteDTO(
                                "Ana Maria Oliveira",
                                "ana.oliveira@yahoo.com.br",
                                "987.654.321-11");

                ClienteResponseDTO usuarioAtualizado = usuarioService.update(id, usuarioRequisicao);

                given()
                                .contentType(ContentType.JSON)
                                .body(usuarioAtualizado)
                                .when().put("/clientes/" + id)
                                .then()
                                .statusCode(200);

                ClienteResponseDTO usuarioResponse = usuarioService.findById(id);
                assertThat(usuarioResponse.nome(), notNullValue());
                assertThat(usuarioResponse.email(), notNullValue());
                assertThat(usuarioResponse.cpf(), is("987.654.321-11"));

        }

        @Test
        public void testDelete() {
                var telefones = new ArrayList<TelefoneDTO>();
                telefones.add(new TelefoneDTO("99", "9 1234-5678"));

                var enderecos = new ArrayList<EnderecoDTO>();
                enderecos.add(new EnderecoDTO(true,
                                "Avenida Paulista",
                                "1234",
                                "Sala 567",
                                "Bela Vista",
                                "01310-100",
                                null));

                ClienteDTO usuario = new ClienteDTO(
                                "Ana Maria Oliveira",
                                "ana.oliveira@yahoo.com.br",
                                "987.654.321-11");

                Long id = usuarioService.create(usuario).id();

                given()
                                .when().delete("/clientes/" + id)
                                .then()
                                .statusCode(204);

                ClienteResponseDTO usuarioResponse = null;
                try {
                        usuarioResponse = usuarioService.findById(id);
                } catch (Exception e) {
                        assert true;
                } finally {
                        assertNull(usuarioResponse);
                }
        }
}