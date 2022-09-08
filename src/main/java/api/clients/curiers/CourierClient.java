package api.clients.curiers;

import api.clients.User;
import api.endpoints.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class CourierClient {


    private static final String AUTHORIZATION = "authorization";

    private static final String PATH = "auth/user";

    @Step("Register new user")
    public ValidatableResponse newUserRegister() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.getRandom())
                .when()
                .post("auth/register")
                .then();
    }

    @Step("Register exist user")
    public ValidatableResponse defaultUserRegister() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.getDefault())
                .when()
                .post("auth/register")
                .then();
    }

    @Step("Register user with empty email")
    public ValidatableResponse emptyEmail() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.emptyEmail())
                .when()
                .post("auth/register")
                .then();
    }

    @Step("Register user with empty password")
    public ValidatableResponse emptyPassword() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.emptyPassword())
                .when()
                .post("auth/register")
                .then();
    }

    @Step("Register user with empty name")
    public ValidatableResponse emptyNameField() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.emptyNameField())
                .when()
                .post("auth/register")
                .then();
    }

    @Step("Enter Data User")
    public ValidatableResponse userEnterLog(User user) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", user.getEmail());
        dataMap.put("name", user.getName());
        dataMap.put("password", user.getPassword());
        return given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .log().all()
                .post("auth/login")
                .then();
    }

    @Step("Update user data change password")
    public ValidatableResponse userUpdate(String accessToken) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("password", "updateUser");
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(dataMap)
                .when()
                .log().all()
                .patch(PATH)
                .then();
    }

    @Step("Get User Info")
    public ValidatableResponse getUserInfo(String accessToken) {
        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .get(PATH)
                .then();
    }

    @Step("User update without Auth")
    public ValidatableResponse userUpdateWithoutAuth() {
        return given()
                .spec(Endpoints.getBaseSpec())
                .body(User.getRandom())
                .when()
                .patch(PATH)
                .then();
    }

    @Step("Remove User")
    public ValidatableResponse userRemove(String accessToken) {

        return given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .delete(PATH)
                .then();
    }
}