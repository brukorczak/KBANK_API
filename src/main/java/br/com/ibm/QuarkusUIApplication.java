package br.com.ibm;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "API BANCO",
                version = "1.0",
                contact = @Contact(
                        name = "Bruna Korczak",
                        url = "https://www.linkedin.com/in/bruna-diele-korczak-trino-03a050205/",
                        email = "bkorczak55@gmail.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class QuarkusUIApplication extends Application {
}