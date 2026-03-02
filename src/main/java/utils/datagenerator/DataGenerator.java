package utils.datagenerator;

import dto.User;
import lombok.Getter;
import org.instancio.Instancio;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import utils.datagenerator.generators.InvalidEmailGenerator;
import utils.datagenerator.generators.InvalidEmailGenerator.EmailErrorType;
import utils.datagenerator.generators.InvalidTelephoneGenerator;
import utils.datagenerator.generators.InvalidTelephoneGenerator.TelephoneErrorType;

import java.util.List;
import java.util.Random;

import static org.instancio.Select.field;
import static org.instancio.Select.root;

public class DataGenerator {
    @Getter
    private final long ORIGINAL_SEED;

    private long seed;


    public DataGenerator() {
        Random random = new Random();
        ORIGINAL_SEED = random.nextLong();
        seed = ORIGINAL_SEED;
    }

    public DataGenerator(long seed) {
        ORIGINAL_SEED = seed;
        this.seed = seed;
    }


    private Settings getSeedSettings() {
        return Settings.create()
                .set(Keys.SEED, nextSeed());
    }

    private long nextSeed() {
        long currentSeed = seed;
        seed++;
        return currentSeed;
    }


    public User generateUser() {
        String telephonePattern = "+" + "#d".repeat(
                Instancio.gen().withSettings(getSeedSettings())
                        .ints().range(7, 15).get());

        User user = Instancio.of(User.class)
                .withSettings(getSeedSettings())
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
                .generate(field("loginName"), gen -> gen.string().mixedCase().alphaNumeric()
                        .minLength(5).maxLength(64))
                .generate(field("password"), gen -> gen.string().mixedCase().alphaNumeric()
                        .minLength(4).maxLength(20))
                .ignore(field("passwordConfirm"))
                .create();

        user.setPasswordConfirm(user.getPassword());
        return user;
    }

    public int generateInt(int minValue, int maxValue) {
        return Instancio.gen().withSettings(getSeedSettings())
                .ints().range(minValue, maxValue).get();
    }

    public <T> T selectRandomOption(List<T> options) {
        int optionIndex = generateInt(0, options.size() - 1);
        return options.get(optionIndex);
    }


    public String randomString(int minLength) {
        return Instancio.gen().withSettings(getSeedSettings())
                .string().mixedCase().minLength(minLength).get();
    }


    public String generateValidEmail() {
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .generate(root(), gen -> gen.net().email())
                .create();
    }

    public String generateRandomInvalidEmail() {
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .supply(root(), new InvalidEmailGenerator())
                .create();
    }

    public String generateInvalidEmail(EmailErrorType errorType) {
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .supply(root(), new InvalidEmailGenerator(errorType))
                .create();
    }


    public String generateValidTelephone() {
        String telephonePattern = "+" + "#d".repeat(
                Instancio.gen().withSettings(getSeedSettings())
                        .ints().range(7, 15).get());
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .generate(root(), gen -> gen.text().pattern(telephonePattern))
                .create();
    }

    public String generateRandomInvalidTelephone() {
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .generate(root(), new InvalidTelephoneGenerator())
                .create();
    }

    public String generateInvalidTelephone(TelephoneErrorType errorType) {
        return Instancio.of(String.class).withSettings(getSeedSettings())
                .generate(root(), new InvalidTelephoneGenerator(errorType))
                .create();
    }
}
