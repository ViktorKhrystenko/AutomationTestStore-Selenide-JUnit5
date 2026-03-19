package dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.openqa.selenium.WebElement;

@EqualsAndHashCode
public class Address {
    @Getter
    private String fullName;
    @Getter
    private String address;
    @Getter
    private String location;
    @Getter
    private String country;


    public Address(WebElement addressElement) {
        String[] addressFields = addressElement.getText().split("\n");
        String[] cityAndZipCode = splitCityAndZipCode(addressFields[2]);
        fullName = addressFields[0];
        address = addressFields[1];
        location = getLocation(cityAndZipCode[0], cityAndZipCode[1], addressFields[3]);
        country = addressFields[4];
    }


    public Address(WebElement fullNameElement, WebElement addressElement) {
        String[] firstAndLastNames = fullNameElement.getText().split("\n");
        String[] addressFields = addressElement.getText().split("\n");
        fullName = getFullName(firstAndLastNames[0], firstAndLastNames[1]);
        address = addressFields[0];
        location = addressFields[1];
        country = addressFields[2];
    }


    public static String getLocation(String city, String zipCode, String regionState) {
        return city + regionState + zipCode;
    }

    public static String getFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }


    private String[] splitCityAndZipCode(String cityAndZipCode) {
        StringBuilder city = new StringBuilder(cityAndZipCode);
        StringBuilder zipCode = new StringBuilder();
        for (int i = cityAndZipCode.length() - 1; i >= 0; i--) {
            if (cityAndZipCode.charAt(i) == ' ') {
                city.deleteCharAt(i);
                break;
            }
            zipCode.append(cityAndZipCode.charAt(i));
            city.deleteCharAt(i);
        }
        return new String[]{city.toString(), zipCode.reverse().toString()};
    }
}
