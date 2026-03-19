package constants.url;

import java.util.regex.Pattern;

import static constants.url.BaseUrls.PRODUCT_BASE_URL;

public class BaseUrlRegexPatterns {
    public static final String PRODUCT_PAGE_REGEX_PATTERN = Pattern.quote(PRODUCT_BASE_URL)
            .replace("&product_id=", "")
            .concat("(&path=\\d+)?&product_id=\\d+");
}
