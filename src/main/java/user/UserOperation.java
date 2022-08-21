package user;

import endpoints.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class UserOperation {

    private final User user = new User();
    @Getter
    private String accessToken;

    private static final String AUTHORIZATION = "authorization";

    private static final String PATH = "auth/user";

    @Getter
    @Setter
    private Response response;

    public void setAccessToken() {
        accessToken = response.then().extract().path("accessToken").toString();
    }

    @Step("Register new user")
    public void newUserRegister() {
        response = given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(User.getRandom())
                .when()
                .post("auth/register");
    }

    @Step("Register exist user")
    public void defaultUserRegister() {
        User defaultUser = new User("uij", "icjdijc@yandex.ru", "idcjidjcidj");
        response = given()
                .spec(Endpoints.getBaseSpec())
                .log().all()
                .body(defaultUser)
                .when()
                .post("auth/register");
    }

    @Step("Enter Data User")
    public void userEnterLog() {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", user.getEmail());
        dataMap.put("name", user.getName());
        dataMap.put("password", user.getPassword());
        response = given()
                .spec(Endpoints.getBaseSpec())
                .body(dataMap)
                .when()
                .log().all()
                .post("auth/login");
    }

    @Step("Update user data change password")
    public void userUpdate(String accessToken) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("password", "updateUser");
        response = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .body(dataMap)
                .when()
                .log().all()
                .patch(PATH);
    }

    @Step("Get User Info")
    public void getUserInfo() {
        response = given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .get(PATH);
    }

    @Step("User update without Auth")
    public void userUpdateWithoutAuth() {
        response = given()
                .spec(Endpoints.getBaseSpec())
                .body(User.getRandom())
                .when()
                .patch(PATH);
    }

    @Step("Remove User")
    public void userRemove() {
        if (getAccessToken() == null) return;
        given()
                .spec(Endpoints.getBaseSpec())
                .headers(AUTHORIZATION, accessToken)
                .when()
                .delete(PATH);
    }
}