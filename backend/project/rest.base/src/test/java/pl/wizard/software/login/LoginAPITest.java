package pl.wizard.software.login;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginAPITest {

    @Test
    public void shouldReturnStatusCode200() {
        String requestBody = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        given().body(requestBody).
        when().post("http://localhost:8080/api/v2/login").
                then().statusCode(200);
    }

}