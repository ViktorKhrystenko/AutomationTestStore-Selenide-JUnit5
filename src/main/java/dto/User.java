package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String fax;

    private String company;
    private String address_1;
    private String address_2;
    private String city;
    private String zipCode;

    private String loginName;
    private String password;
    private String passwordConfirm;
}
