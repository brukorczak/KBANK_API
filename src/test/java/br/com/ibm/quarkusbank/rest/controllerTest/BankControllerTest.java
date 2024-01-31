//package br.com.ibm.quarkusbank.rest.controllerTest;
//
//import br.com.ibm.persistence.dto.AddAccountDto;
//import br.com.ibm.persistence.model.AccountType;
//import br.com.ibm.services.BankService;
//import io.quarkus.test.common.http.TestHTTPResource;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.*;
//
//import java.net.URL;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@QuarkusTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class BankControllerTest {
//
//    @TestHTTPResource("/api/v1/users")
//    URL apiUrl;
//
//    @Inject
//    BankService bankService;
//
//    @Test
//    @Order(1)
//    public void testCreateAccount_Success() {
//        // Crie um usuário para o teste
//        Long userId = createUserForTest();
//
//        // Dados de entrada para a criação da conta
//        AddAccountDto addAccountDto = new AddAccountDto();
//        addAccountDto.setAccountType(AccountType.CURRENT); // ou AccountType.SAVINGS conforme necessário
//
//        // Chame o endpoint de criação de conta
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(addAccountDto)
//                .pathParam("id", userId)
//                .when()
//                .post(apiUrl + "/" + userId + "/accounts");
//
//        // Verifique se a resposta foi bem-sucedida (status 201)
//        assertEquals(201, response.getStatusCode());
//    }
//
//    @Test
//    @Order(2)
//    public void testCreateAccount_UserNotFound() {
//        // Tente criar uma conta para um usuário inexistente
//        Long nonExistingUserId = 999L; // Um ID que não existe
//
//        // Dados de entrada para a criação da conta
//        AddAccountDto addAccountDto = new AddAccountDto();
//        addAccountDto.setAccountType(AccountType.CURRENT); // ou AccountType.SAVINGS conforme necessário
//
//        // Chame o endpoint de criação de conta
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .body(addAccountDto)
//                .pathParam("id", nonExistingUserId)
//                .when()
//                .post(apiUrl + "/" + nonExistingUserId + "/accounts");
//
//        // Verifique se a resposta indica que o usuário não foi encontrado (status 404)
//        assertEquals(404, response.getStatusCode());
//    }
//
//    // Método auxiliar para criar um usuário para os testes
//    private Long createUserForTest() {
//        // Crie um usuário de teste
//        // Certifique-se de adaptar isso à sua lógica real de criação de usuário
//        // Este é um exemplo básico
//        // Certifique-se de substituir ou ajustar conforme necessário
//        Response createUserResponse = given()
//                .contentType(ContentType.JSON)
//                .body("{ \"name\": \"Test User\", \"age\": 25, \"phone\": \"123456789\", \"address\": \"Test Address\", \"cpf\": \"CPF123456\" }")
//                .when()
//                .post(apiUrl);
//
//        // Extrair o ID do usuário criado
//        String locationHeader = createUserResponse.getHeader("Location");
//        assertNotNull(locationHeader, "Location header não encontrado");
//        String[] parts = locationHeader.split("/");
//        return Long.parseLong(parts[parts.length - 1]);
//    }
//}
