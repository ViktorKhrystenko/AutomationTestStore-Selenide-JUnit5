package utils.datagenerator;

import dto.User;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class DataGenerator {

    public User generateUser() {
        String telephonePattern = "+" + "#d".repeat(
                Instancio.gen().ints().range(7, 15).get());

        User user = Instancio.of(User.class)
                .generate(field("firstName"), gen -> gen.string().mixedCase()
                        .minLength(1).maxLength(32))
                .generate(field("lastName"), gen -> gen.string().mixedCase()
                        .minLength(1).maxLength(32))
                .generate(field("email"), gen -> gen.net().email())
                .generate(field("telephone"), gen -> gen.text().pattern(telephonePattern))
                .generate(field("fax"), gen -> gen.text().pattern(telephonePattern))
                .generate(field("company"), gen -> gen.string().mixedCase().alphaNumeric())
                .generate(field("address_1"), gen -> gen.string().mixedCase().alphaNumeric()
                        .minLength(3).maxLength(128))
                .generate(field("address_2"), gen -> gen.string().mixedCase().alphaNumeric()
                        .minLength(3).maxLength(128))
                .generate(field("city"), gen -> gen.string().mixedCase()
                        .minLength(3).maxLength(128))
                .generate(field("zipCode"), gen -> gen.string().digits()
                        .minLength(3).maxLength(10))
                .generate(field("loginName"), gen -> gen.string().mixedCase()
                        .minLength(5).maxLength(64))
                .generate(field("password"), gen -> gen.string().mixedCase().alphaNumeric()
                        .minLength(4).maxLength(20))
                .ignore(field("passwordConfirm"))
                .create();

        user.setPasswordConfirm(user.getPassword());
        return user;
    }
}
