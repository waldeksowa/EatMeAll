package pl.wizard.software.login;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginAPITest {

    private static String requestBody = "{\"username\": \"admin\", \"password\": \"password\"}";

    @Test
    public void shouldReturnStatusCode200() {
        given().contentType(ContentType.JSON).body(requestBody).
        when().post("http://localhost:8080/api/v2/login").
        then().statusCode(200);
    }

    @Test
    public void shouldReturnValidResponse() {
        
    }

}