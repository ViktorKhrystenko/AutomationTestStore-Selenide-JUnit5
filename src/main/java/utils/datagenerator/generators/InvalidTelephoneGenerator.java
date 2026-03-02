package utils.datagenerator.generators;

import org.instancio.Random;
import org.instancio.generator.Generator;

public class InvalidTelephoneGenerator implements Generator<String> {
    private static final String FORBIDDEN_SPECIAL_CHARACTERS = " .-_(),:;<>@[\\]\"!#$%&'*/=?^`{|}~";


    public enum TelephoneErrorType {
        TOO_SHORT,
        TOO_LONG,
        WITH_SPECIAL_CHARACTERS,
        WITH_LETTERS
    }


    private TelephoneErrorType errorType;


    public InvalidTelephoneGenerator() {}

    public InvalidTelephoneGenerator(TelephoneErrorType errorType) {
        this.errorType = errorType;
    }


    @Override
    public String generate(Random random) {
        String pattern = "+%s";
        String patternAddition;
        int numberLength = random.intRange(7, 15);

        if (errorType == null) {
            errorType = random.oneOf(TelephoneErrorType.values());
        }

        switch (errorType) {
            case TOO_SHORT:
                numberLength = random.intRange(1, 6);
                patternAddition = random.digits(numberLength);
                break;

            case TOO_LONG:
                numberLength = random.intRange(16, 20);
                patternAddition = random.digits(numberLength);
                break;

            case WITH_SPECIAL_CHARACTERS:
                String invalidCharacter = String.valueOf(FORBIDDEN_SPECIAL_CHARACTERS
                        .charAt(random.intRange(0, FORBIDDEN_SPECIAL_CHARACTERS.length())));
                patternAddition = random.digits(numberLength - 1) + invalidCharacter;
                break;

            case WITH_LETTERS:
                patternAddition = random.digits(numberLength - 1) + random.alphanumeric(1);
                break;

            default:
                patternAddition = random.digits(numberLength - 1) + random.alphanumeric(1);
                break;
        }

        return String.format(pattern, patternAddition);
    }
}
