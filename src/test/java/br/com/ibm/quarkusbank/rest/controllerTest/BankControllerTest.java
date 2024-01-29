package br.com.ibm.quarkusbank.rest.controllerTest;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.URL;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankControllerTest {

    @TestHTTPResource("/v1/users")
    URL apiUrl;


}
