package user;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class EnterDataUserNegativeTest {

    private UserOperation getUserOperation;

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
    @DisplayName("Auth user with incorrect pass log")
    @Description("401 Unauthorized")
    public void authIncorrectPassLogg() {
        getUserOperation.userEnterLog();
        getUserOperation.getResponse()
                .then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }


}
