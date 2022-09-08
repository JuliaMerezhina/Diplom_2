package api.clients.curiers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateUserWithEmptyFieldsTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Impossible reg user without email-field")
    @Description("403 Forbidden")
    public void userImpossibleRegWithoutEmail() {
        ValidatableResponse response = courierClient.emptyEmail();
        response.assertThat().statusCode(403)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Impossible reg user without password-field")
    @Description("403 Forbidden")
    public void userImpossibleRegWithoutPass() {
        ValidatableResponse response = courierClient.emptyPassword();
        response.assertThat().statusCode(403)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("impossible reg user without name-field")
    @Description("403 Forbidden")
    public void userCreationWithoutNameNotPossible() {
        ValidatableResponse response = courierClient.emptyNameField();
        response.assertThat().statusCode(403)
                .and()
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));


    }


}
