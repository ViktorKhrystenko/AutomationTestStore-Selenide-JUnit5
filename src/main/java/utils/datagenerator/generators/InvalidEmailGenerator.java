package utils.datagenerator.generators;

import org.instancio.Random;
import org.instancio.generator.Generator;

public class InvalidEmailGenerator implements Generator<String> {
    private static final String EMAIL_FORBIDDEN_SPECIAL_CHARACTERS = " (),:;<>@[\\]\"!#$%&'*/=?^`{|}~";


    public enum EmailErrorType {
        PREFIX_STARTS_WITH_DOT,
        PREFIX_ENDS_WITH_DOT,
        PREFIX_WITH_DOUBLE_DOT,
        PREFIX_WITH_INVALID_CHAR,

        DOMAIN_STARTS_WITH_DOT,
        DOMAIN_WITH_DOUBLE_DOT,
        DOMAIN_WITH_INVALID_CHAR,

        DNS_DOMAIN_WITH_ONE_CHAR,

        MISSING_PREFIX,
        MISSING_AT_CHAR,
        MISSING_DOMAIN,
        MISSING_DNS_DOMAIN,

        PLAIN_TEXT
    }


    private EmailErrorType errorType;


    public InvalidEmailGenerator() {}

    public InvalidEmailGenerator(EmailErrorType errorType) {
        this.errorType = errorType;
    }


    @Override
    public String generate(Random random) {
        int prefixLength = random.intRange(1, 20);
        int domainLength = random.intRange(1, 7);
        int dnsDomainLength = random.intRange(2, 5);

        String prefix = random.alphanumeric(prefixLength);
        String domain = random.alphanumeric(domainLength);
        String dnsDomain = random.alphanumeric(dnsDomainLength);

        if (errorType == null) {
            errorType = random.oneOf(EmailErrorType.values());
        }

        switch (errorType) {
            case PREFIX_STARTS_WITH_DOT: {
                return "." + prefix + "@" + domain + "." + dnsDomain;
            }
            case PREFIX_ENDS_WITH_DOT: {
                return prefix + ".@" + domain + "." + dnsDomain;
            }
            case PREFIX_WITH_DOUBLE_DOT: {
                return prefix + ".." + prefix + "@" + domain + "." + dnsDomain;
            }
            case PREFIX_WITH_INVALID_CHAR: {
                String invalidChar = getRandomInvalidChar(random);
                return prefix + invalidChar + prefix + "@" + domain + "." + dnsDomain;
            }

            case DOMAIN_STARTS_WITH_DOT: {
                return prefix + "@." +  domain + "." + dnsDomain;
            }
            case DOMAIN_WITH_DOUBLE_DOT: {
                return prefix + "@" + domain + ".." +  domain + "." + dnsDomain;
            }
            case DOMAIN_WITH_INVALID_CHAR: {
                String invalidChar = getRandomInvalidChar(random);
                return prefix + "@" + domain + invalidChar + domain + "." + dnsDomain;
            }

            case DNS_DOMAIN_WITH_ONE_CHAR: {
                return prefix + "@" + domain + "." + dnsDomain.charAt(0);
            }

            case MISSING_PREFIX: {
                return "@" + domain + "." + dnsDomain;
            }
            case MISSING_AT_CHAR: {
                return prefix + domain + "." + dnsDomain;
            }
            case MISSING_DOMAIN: {
                return prefix + "@";
            }
            case MISSING_DNS_DOMAIN: {
                return prefix + "@" + domain;
            }

            case PLAIN_TEXT: {
                return random.alphanumeric(15);
            }

            default:
                return random.alphanumeric(15);
        }
    }


    private String getRandomInvalidChar(Random random) {
        return String.valueOf(EMAIL_FORBIDDEN_SPECIAL_CHARACTERS
                .charAt(random.intRange(0, EMAIL_FORBIDDEN_SPECIAL_CHARACTERS.length() - 1)));
    }
}
