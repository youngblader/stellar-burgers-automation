package utils;

import models.User;
import java.util.UUID;

public class UserGenerator {
    public static User generateUser() {
        String name = "Eugene";
        String password = "qwer123";
        String email = "evgeniy@mail.ru" + UUID.randomUUID();

        return new User(name, password, email);
    }
}