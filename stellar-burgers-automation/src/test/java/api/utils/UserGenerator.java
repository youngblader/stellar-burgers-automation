package api.utils;

import models.User;
import net.datafaker.Faker;

public class UserGenerator {
    public static User generateUser() {
        Faker faker = new Faker();

        String name = faker.name().fullName();
        String password = faker.credentials().password();
        String email = faker.internet().emailAddress();

        return new User(name, password, email);
    }
}