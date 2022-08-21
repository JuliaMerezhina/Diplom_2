package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private String email;

    private String password;

    public static User getRandom() {
        String name = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(15) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10);
        return new User(name, email, password);
    }
}