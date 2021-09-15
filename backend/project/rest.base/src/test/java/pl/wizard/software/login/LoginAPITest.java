package pl.wizard.software.login;

import org.junit.Test;

public class LoginAPITest {

    private static final String REQUEST_BODY = "{\"username\": \"admin\", \"password\": \"password\"}";

    @Test
    public void shouldReturnStatusCode200AndExpectedId() {
//        given().contentType(ContentType.JSON).body(REQUEST_BODY).
//        when().post("http://localhost:8080/api/v2/login").
//        then().statusCode(200).body("accountID", equalTo(1));
    }

    @Test
    public void shouldReturnValidResponseBody() {
//        given().contentType(ContentType.JSON).body(REQUEST_BODY).
//        when().post("http://localhost:8080/api/v2/login").
//        then().assertThat().body(matchesJsonSchemaInClasspath("token-schema.json"));
    }

}