package user;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class DataUserUpdateTest {

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
    @DisplayName("Update data auth user")
    @Description("200 ОК")
    public void userUpdate() {
        getUserOperation.newUserRegister();
        getUserOperation.setAccessToken();
        user.getRandom();
        getUserOperation.userUpdate(getUserOperation.getAccessToken());
        getUserOperation.getUserInfo();
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("User update without Auth")
    @Description("401 Unauthorized")
    public void userUpdateWithoutAuth() {
        getUserOperation.userUpdateWithoutAuth();
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false));
    }
}
