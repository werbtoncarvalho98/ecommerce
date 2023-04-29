package br.unitins.topicos1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ProdutoDTO;
import br.unitins.topicos1.dto.ProdutoResponseDTO;
import br.unitins.topicos1.service.ProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ProdutoResourceTest {

    @Inject
    ProdutoService produtoService;

    @Test
    public void getAllTest() {
        given()
                .when().get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        ProdutoDTO produtos = new ProdutoDTO("Placa de Video", "8GB GDDR6", 3499.90f, 100);

        given()
                .contentType(ContentType.JSON)
                .body(produtos)
                .when().post("/produtos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Placa de Video"),
                        "descricao", is("8GB GDDR6"),
                        "preco", is(3499.90f),
                        "estoque", is(100));
    }

    @Test
    public void testUpdate() {
        ProdutoDTO produtos = new ProdutoDTO("Placa de Video", "8GB GDDR6", 3499.90f, 100);
        Long id = produtoService.create(produtos).id();

        ProdutoDTO produtosUpdate = new ProdutoDTO("Processador", "8 Nucleos 16 Threads", 2499.90f, 100);

        given()
                .contentType(ContentType.JSON)
                .body(produtosUpdate)
                .when().put("/produtos/" + id)
                .then()
                .statusCode(204);

        ProdutoResponseDTO produtosResponse = produtoService.findById(id);
        assertThat(produtosResponse.nome(), is("Processador"));
        assertThat(produtosResponse.descricao(), is("8 Nucleos 16 Threads"));
        assertThat(produtosResponse.preco(), is(2499.90f));
        assertThat(produtosResponse.estoque(), is(100));
    }

    @Test
    public void testDelete() {
        ProdutoDTO produtos = new ProdutoDTO("Placa de Video", "8GB GDDR6", 3499.90f, 100);
        Long id = produtoService.create(produtos).id();

        given()
                .when().delete("/produtos/" + id)
                .then()
                .statusCode(204);

        ProdutoResponseDTO produtosResponse = null;
        try {
            produtosResponse = produtoService.findById(id);
        } catch (Exception e) {

        } finally {
            assertNull(produtosResponse);
        }
    }
}