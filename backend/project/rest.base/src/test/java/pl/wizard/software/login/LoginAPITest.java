package pl.wizard.software.login;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginAPITest {

    private static String requestBody = "{\"username\": \"admin\", \"password\": \"password\"}";

    @Test
    public void shouldReturnStatusCode200AndExpectedId() {
        given().contentType(ContentType.JSON).body(requestBody).
        when().post("http://localhost:8080/api/v2/login").
        then().statusCode(200).body("accountID", equalTo(1));
    }

    @Test
    public void shouldReturnValidResponseBody() {
        given().contentType(ContentType.JSON).body(requestBody).
        when().post("http://localhost:8080/api/v2/login").
        then().assertThat().body(matchesJsonSchemaInClasspath("token-schema.json"));
    }

}