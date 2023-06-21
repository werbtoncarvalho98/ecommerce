// package br.unitins.topicos1;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.CoreMatchers.notNullValue;
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.junit.jupiter.api.Assertions.assertNull;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import br.unitins.topicos1.dto.AuthUsuarioDTO;
// import br.unitins.topicos1.dto.EstadoDTO;
// import br.unitins.topicos1.dto.EstadoResponseDTO;
// import br.unitins.topicos1.service.EstadoService;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;
// import jakarta.inject.Inject;

// @QuarkusTest
// public class EstadoResourceTest {

//     @Inject
//     EstadoService estadoService;

//     private String token;

//     @BeforeEach
//     public void setUp() {
//         var auth = new AuthUsuarioDTO("joao", "abc123");

//         Response response = (Response) given()
//                 .contentType("application/json")
//                 .body(auth)
//                 .when().post("/auth")
//                 .then()
//                 .statusCode(200)
//                 .extract()
//                 .response();

//         token = response.header("Authorization");
//     }

//     @Test
//     public void getAllTest() {
//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .when().get("/estados")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void testInsert() {
//         EstadoDTO estados = new EstadoDTO("Tocantins", "TO");

//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .contentType(ContentType.JSON)
//                 .body(estados)
//                 .when().post("/estados")
//                 .then()
//                 .statusCode(201)
//                 .body("id", notNullValue(),
//                         "nome", is("Tocantins"),
//                         "sigla", is("TO"));
//     }

//     @Test
//     public void testUpdate() {
//         EstadoDTO estados = new EstadoDTO("Tocantins", "TO");
//         Long id = estadoService.create(estados).id();

//         EstadoDTO estadosUpdate = new EstadoDTO("Maranhao", "MA");

//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .contentType(ContentType.JSON)
//                 .body(estadosUpdate)
//                 .when().put("/estados/" + id)
//                 .then()
//                 .statusCode(204);

//         EstadoResponseDTO estadosResponse = estadoService.findById(id);
//         assertThat(estadosResponse.nome(), is("Maranhao"));
//         assertThat(estadosResponse.sigla(), is("MA"));
//     }

//     @Test
//     public void testDelete() {
//         EstadoDTO estados = new EstadoDTO("Tocantins", "TO");
//         Long id = estadoService.create(estados).id();

//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .when().delete("/estados/" + id)
//                 .then()
//                 .statusCode(204);

//         EstadoResponseDTO estadosResponse = null;
//         try {
//             estadosResponse = estadoService.findById(id);
//         } catch (Exception e) {

//         } finally {
//             assertNull(estadosResponse);
//         }
//     }

//     @Test
//     public void testFindById() {
//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .when().get("/estados/1")
//                 .then()
//                 .statusCode(200)
//                 .body(notNullValue());
//     }

//     @Test
//     public void testCount() {
//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .when().get("/estados/count")
//                 .then()
//                 .statusCode(200)
//                 .body(notNullValue());
//     }

//     @Test
//     public void testSearch() {
//         given()
//                 .header("Authorization", "Bearer " + token)
//                 .when().get("/estados/search/tocantins")
//                 .then()
//                 .statusCode(200)
//                 .body(notNullValue());
//     }
// }