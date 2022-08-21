package user;

import endpoints.Endpoints;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserRegisterTest {

    private UserOperation getUserOperation;

    private final User user = new User();

    @Before
    public void setUp() {
        User.getRandom();
        getUserOperation = new UserOperation();
    }

    @After
    public void tearDown() {
        getUserOperation.userRemove();
    }


    @Test
    @DisplayName("New user register")
    @Description("200 OK")
    public void newUserRegister() {
        getUserOperation.newUserRegister();
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("User already exist")
    @Description("403 Forbidden")
    public void newUserRegExist() {

        getUserOperation.defaultUserRegister();
        getUserOperation.defaultUserRegister();
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Impossible reg user without email-field")
    @Description("403 Forbidden")
    public void userImpossibleRegWithoutEmail() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("password", user.getPassword());
        dataMap.put("name", user.getName());
        getUserOperation.setResponse(given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .post("auth/register"));
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields")
                );
    }

    @Test
    @DisplayName("Impossible reg user without password-field")
    @Description("403 Forbidden")
    public void userImpossibleRegWithoutPass() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("password", user.getEmail());
        dataMap.put("name", user.getName());
        getUserOperation.setResponse(given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .post("auth/register"));
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields")
                );
    }

    @Test
    @DisplayName("impossible reg user without name-field")
    @Description("403 Forbidden")
    public void userCreationWithoutNameNotPossible() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("password", user.getEmail());
        dataMap.put("name", user.getPassword());
        getUserOperation.setResponse(given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .post("auth/register"));
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields")
                );
    }


}
