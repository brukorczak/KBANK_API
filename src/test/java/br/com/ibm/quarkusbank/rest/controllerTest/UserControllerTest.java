package br.com.ibm.quarkusbank.rest.controllerTest;

import br.com.ibm.persistence.dto.AddUserDto;
import br.com.ibm.persistence.model.AccountType;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @TestHTTPResource("/v1/users")
    URL apiUrl;

    //teste para criar usuário com sucesso
    @Test
    @DisplayName("should create an user successfully")
    @Order(1)
    public void createUserTest() {
        var user = new AddUserDto();
        user.setName("Fulano");
        user.setAge(30);
        user.setPhone("19991154069");
        user.setAddress("SP");
        user.setAccountType(AccountType.CURRENT);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                        .when()
                        .post(apiUrl);

        assertEquals(201, response.statusCode());

        String responseBody = response.getBody().asString();
        assertNotNull(responseBody, "Resposta vazia");
        assertJson(responseBody);
    }

    private void assertJson(String jsonString) {
        assertNotNull(jsonString, "A resposta não está no formato JSON");
    }

    //teste para listar todos os usuários
    @Test
    @DisplayName("should list all users")
    @Order(2)
    public void listAllUsersTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(apiUrl)
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }

    //teste para atualizar usuário com sucesso
    @Test
    @DisplayName("should update user successfully")
    @Order(3)
    public void updateUserTest() {
        var updateUser = new AddUserDto();
        updateUser.setName("NovoNome");
        updateUser.setAge(25);
        updateUser.setPhone("19991154069");
        updateUser.setAddress("NovaSP");
        updateUser.setAccountType(AccountType.SAVINGS);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .when()
                        .put(apiUrl + "/1");

        assertEquals(204, response.statusCode());
    }

    //teste que não deve atualiza usuário inexistente
    @Test
    @DisplayName("should not update non-existing user")
    @Order(4)
    public void updateNonExistingUserTest() {
        var updateUser = new AddUserDto();
        updateUser.setName("NovoNome");
        updateUser.setAge(25);
        updateUser.setPhone("19991154123");
        updateUser.setAddress("NovaSP");
        updateUser.setAccountType(AccountType.SAVINGS);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .when()
                        .put(apiUrl + "/999");

        assertEquals(400, response.statusCode());
    }

    //teste exclui o usuário com sucesso
    @Test
    @DisplayName("should delete user successfully")
    @Order(5)
    public void deleteUserTest() {
        Response response =
                given()
                        .when()
                        .delete(apiUrl + "/1");

        assertEquals(204, response.statusCode());
    }

    //teste não deve excluir usuário inexistente
    @Test
    @DisplayName("should not delete non-existing user")
    @Order(6)
    public void deleteNonExistingUserTest() {
        Response response =
                given()
                        .when()
                        .delete(apiUrl + "/999");

        assertEquals(404, response.statusCode());
    }
}
