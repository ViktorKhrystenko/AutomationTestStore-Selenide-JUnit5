# 1. Registration tests

## 1.1. Positive tests

### 1.1.1. General tests

#### 1.1.1.1. Test case - Entrance test

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/

| **Step**                                                                                                                       | **Test data** | **Expected result**                                                          | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------|---------------|------------------------------------------------------------------------------|-------------------|
| 1. Click on "Login or register" link on nav bar.                                                                               |               | The user is on https://automationteststore.com/index.php?rt=account/login .  |                   |
| 2. On "I am a new customer." section, select "Register Account" radio button (if not selected) and click on "Continue" button. |               | The user is on https://automationteststore.com/index.php?rt=account/create . |                   |

#### 1.1.1.2. Test case - Check user registration with only necessary fields filled

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                                | **Test data**                                                                                                                             | **Expected result**                                                                                    | **Actual result** |
|---------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Select "Country" (any).                                                                                                                              |                                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 3. Select "Region / State" (any).                                                                                                                       |                                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 4. Check "Privacy Policy" checkbox.                                                                                                                     |                                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 5. Push "Continue" button.                                                                                                                              |                                                                                                                                           | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

#### 1.1.1.3. Test case - Check user registration with all fields filled

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                                | **Test data**                                                                                                                             | **Expected result**                                                                                    | **Actual result** |
|---------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP code: <br/>Login name: <br/>Password: <br/>Password confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Fill "Telephone" field.                                                                                                                              | Phone number:                                                                                                                             | Entered phone number must be visible inside "Telephone" field.                                         |                   |
| 3. Fill "Fax" field.                                                                                                                                    | Fax:                                                                                                                                      | Entered fax must be visible inside "Fax" field.                                                        |                   |
| 4. Fill "Company" field.                                                                                                                                | Company:                                                                                                                                  | Entered company name must be visible inside "Company" field.                                           |                   |
| 5. Fill "Address 2" field.                                                                                                                              | Address 2:                                                                                                                                | Entered address must be visible inside "Address 2" field.                                              |                   |
| 6. Select "Country" (any).                                                                                                                              |                                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 7. Select "Region / State" (any).                                                                                                                       |                                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 8. Check "Privacy Policy" checkbox.                                                                                                                     |                                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 9. Push "Continue" button.                                                                                                                              |                                                                                                                                           | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

### 1.1.2. "First Name" field tests

#### 1.1.2.1. Test case - First name with *space* ' - characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                    | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter first name with *space* ' - characters in "First Name" field.                                                                    | First Name:                                                                                                              | Entered first name must be visible inside "First Name" field.                                          |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

### 1.1.3. "Last Name" field tests

#### 1.1.3.1. Test case - Last name with *space* ' - characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                    | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter last name with *space* ' - characters in "Last Name" field.                                                                       | Last Name:                                                                                                                | Entered last name must be visible inside "Last Name" field.                                            |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                           | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

### 1.1.4. "City" fields tests

#### 1.1.4.1. Test case - City with *space* - ' . characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                       | **Test data**                                                                                                                  | **Expected result**                                                                                    | **Actual result** |
|------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name" "E-mail", "Address 1", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>Last Name: <br/>E-mail: <br/>Address 1: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter city with *space* - ' . characters in "City" field.                                                                                   | City:                                                                                                                          | Entered city must be visible inside "City" field.                                                      |                   |
| 3. Select "Country" (any).                                                                                                                     |                                                                                                                                | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                              |                                                                                                                                | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                            |                                                                                                                                | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                     |                                                                                                                                | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

### 1.1.5. "ZIP Code" fields tests

#### 1.1.5.1. Test case - ZIP code with *space* - characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                              | **Expected result**                                                                                    | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name" "E-mail", "Address 1", "City", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter ZIP code with *space* - characters in "ZIP code" field.                                                                           | ZIP code:                                                                                                                  | Entered ZIP code must be visible inside "ZIP Code" field.                                              |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                            | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                            | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                            | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                            | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

### 1.1.6. "Region / State" and "Country" dropdowns tests

#### 1.1.6.1 Test case - Does "Region / State" drops after "Country" changed

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                          | **Test data** | **Expected result**                                                                                                                              | **Actual result** |
|-----------------------------------|---------------|--------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Select "Region / State" (any). |               | Selected region/state must be visible on top of "Region / State" dropdown.                                                                       |                   |
| 2. Select "Country" (any).        |               | Selected country must be visible on top of "Country" dropdown. Value in "Region / State" dropdown must change back to " --- Please Select --- ". |                   |

### 1.1.7. "Login name" field tests

#### 1.1.7.1. Test case - Login name with non a-z letters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                    | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter login name with non a-z letters in "Login name" field.                                                                           | Login name:                                                                                                              | Entered login name must be visible inside "Login name" field.                                          |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

#### 1.1.7.2. Test case - Login name with *space* . - characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                    | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                |                   |
| 2. Enter login name with *space* . - characters in "Login name" field.                                                                    | Login name:                                                                                                              | Entered login name must be visible inside "Login name" field.                                          |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                         |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success . |                   |

## 1.2. Negative tests

### 1.2.1. "First Name" field tests

#### 1.2.1.1. Test case - Empty "First Name" field

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                                                                        | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                    |                   |
| 2. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                                                                             |                   |
| 3. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                 |                   |
| 4. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                                                                      |                   |
| 5. Push "Continue" button.                                                                                                                |                                                                                                                          | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "empty first name field" prompt has to appear. |                   |

#### 1.2.1.2. Test case - Too long first name

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                                                                     | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                 |                   |
| 2. Enter first name with more than 32 characters in "First Name" field.                                                                   | First Name:                                                                                                              | Entered first name must be visible inside "First Name" field.                                                                                           |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                                                                          |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                                                                              |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                                                                   |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "too long first name" prompt has to appear. |                   |

#### 1.2.1.3. Test case - First name with numbers

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                                                                         | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                     |                   |
| 2. Enter first name with numbers in "First Name" field.                                                                                   | First Name:                                                                                                              | Entered first name must be visible inside "First Name" field.                                                                                               |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                                                                              |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                  |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                                                                       |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "first name with numbers" prompt has to appear. |                   |

#### 1.2.1.4. Test case - First name with only spaces

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                                                                                               | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                                           |                   |
| 2. Enter some space characters into "First Name" field.                                                                                   |                                                                                                                          | Entered first name must be visible inside "First Name" field.                                                                                                                     |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                                                                                                    |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                                        |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                                                                                             |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . Spaces must be trimmed and "empty first name field" prompt has to appear. |                   |

#### 1.2.1.5. Test case - First name with special characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                  | **Test data**                                                                                                            | **Expected result**                                                                                                                                                    | **Actual result** |
|-------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "Last Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | Last Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                                |                   |
| 2. Enter first name with special characters in "First Name" field.                                                                        | First Name:                                                                                                              | Entered first name must be visible inside "First Name" field.                                                                                                          |                   |
| 3. Select "Country" (any).                                                                                                                |                                                                                                                          | Selected country must be visible on top of "Country" dropdown.                                                                                                         |                   |
| 4. Select "Region / State" (any).                                                                                                         |                                                                                                                          | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                       |                                                                                                                          | "Privacy Policy" checkbox is checked.                                                                                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                |                                                                                                                          | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "first name with special characters" prompt has to appear. |                   |

### 1.2.2. "Last Name" field tests

#### 1.2.2.1. Test case - Empty "Last Name" field

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                                                                       | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                   |                   |
| 2. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                                                                            |                   |
| 3. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                |                   |
| 4. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                                                                     |                   |
| 5. Push "Continue" button.                                                                                                                 |                                                                                                                           | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "empty last name field" prompt has to appear. |                   |

#### 1.2.2.2. Test case - Too long last name

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                                                                    | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                |                   |
| 2. Enter last name with more than 32 characters in "First Name" field.                                                                     | Last Name:                                                                                                                | Entered last name must be visible inside "Last Name" field.                                                                                            |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                                                                         |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                                                                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                           | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "too long last name" prompt has to appear. |                   |

#### 1.2.2.3. Test case - Last name with numbers

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                                                                        | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                    |                   |
| 2. Enter last name with numbers in "First Name" field.                                                                                     | Last Name:                                                                                                                | Entered last name must be visible inside "Last Name" field.                                                                                                |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                                                                             |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                 |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                                                                      |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                           | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "last name with numbers" prompt has to appear. |                   |

#### 1.2.2.4. Test case - Last name with only spaces

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                                                                                              | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                                          |                   |
| 2. Enter some space characters into "Last Name" field.                                                                                     |                                                                                                                           | Entered last name must be visible inside "Last Name" field.                                                                                                                      |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                                                                                                   |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                                       |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                                                                                            |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                           | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . Spaces must be trimmed and "empty last name field" prompt has to appear. |                   |

#### 1.2.2.5. Test case - Last name with special characters

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                   | **Test data**                                                                                                             | **Expected result**                                                                                                                                                   | **Actual result** |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "E-mail", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First Name: <br/>E-mail: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                               |                   |
| 2. Enter last name with special characters in "Last Name" field.                                                                           | Last Name:                                                                                                                | Entered last name must be visible inside "Last Name" field.                                                                                                           |                   |
| 3. Select "Country" (any).                                                                                                                 |                                                                                                                           | Selected country must be visible on top of "Country" dropdown.                                                                                                        |                   |
| 4. Select "Region / State" (any).                                                                                                          |                                                                                                                           | Selected region/state must be visible on top of "Region / State" dropdown.                                                                                            |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                        |                                                                                                                           | "Privacy Policy" checkbox is checked.                                                                                                                                 |                   |
| 6. Push "Continue" button.                                                                                                                 |                                                                                                                           | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "last name with special characters" prompt has to appear. |                   |

### 1.2.3. "Email" field tests

#### 1.2.3.1. Test case - Empty "Email" field

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                      | **Test data**                                                                                                                | **Expected result**                                                                                                                                   | **Actual result** |
|-----------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                               |                   |
| 2. Select "Country" (any).                                                                                                                    |                                                                                                                              | Selected country must be visible on top of "Country" dropdown.                                                                                        |                   |
| 3. Select "Region / State" (any).                                                                                                             |                                                                                                                              | Selected region/state must be visible on top of "Region / State" dropdown.                                                                            |                   |
| 4. Check "Privacy Policy" checkbox.                                                                                                           |                                                                                                                              | "Privacy Policy" checkbox is checked.                                                                                                                 |                   |
| 5. Push "Continue" button.                                                                                                                    |                                                                                                                              | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "empty email field" prompt has to appear. |                   |

#### 1.2.3.2. Test case - Invalid email

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                      | **Test data**                                                                                                                | **Expected result**                                                                                                                               | **Actual result** |
|-----------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                           |                   |
| 2. Enter email, that is not valid according to email regex ( https://emailregex.com/ ), in "Email" field.                                     | Email:                                                                                                                       | Entered email is visible inside "Email" field.                                                                                                    |                   |
| 3. Select "Country" (any).                                                                                                                    |                                                                                                                              | Selected country must be visible on top of "Country" dropdown.                                                                                    |                   |
| 4. Select "Region / State" (any).                                                                                                             |                                                                                                                              | Selected region/state must be visible on top of "Region / State" dropdown.                                                                        |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                           |                                                                                                                              | "Privacy Policy" checkbox is checked.                                                                                                             |                   |
| 6. Push "Continue" button.                                                                                                                    |                                                                                                                              | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "invalid email" prompt has to appear. |                   |

#### 1.2.3.3. Test case - Registration with already used email

Priority:

Status:

Preconditions: the user is on https://automationteststore.com/index.php?rt=account/create

| **Step**                                                                                                                                       | **Test data**                                                                                                                | **Expected result**                                                                                                                                    | **Actual result** |
|------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| 1. Fill "First Name", "Last Name", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values.  | First name: <br/>Last Name: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                |                   |
| 2. Enter valid email in "Email" field.                                                                                                         | Email:                                                                                                                       | Entered email is visible inside "Email" field.                                                                                                         |                   |
| 3. Select "Country" (any).                                                                                                                     |                                                                                                                              | Selected country must be visible on top of "Country" dropdown.                                                                                         |                   |
| 4. Select "Region / State" (any).                                                                                                              |                                                                                                                              | Selected region/state must be visible on top of "Region / State" dropdown.                                                                             |                   |
| 5. Check "Privacy Policy" checkbox.                                                                                                            |                                                                                                                              | "Privacy Policy" checkbox is checked.                                                                                                                  |                   |
| 6. Push "Continue" button.                                                                                                                     |                                                                                                                              | Successful registration. The user is on https://automationteststore.com/index.php?rt=account/success .                                                 |                   |
| 7. Click on "Logoff" button in "MY ACCOUNT" section.                                                                                           |                                                                                                                              | Successful log out. The user is on https://automationteststore.com/index.php?rt=account/logout .                                                       |                   |
| 8. Click on "Login or register" link on nav bar.                                                                                               |                                                                                                                              | The user is on https://automationteststore.com/index.php?rt=account/login .                                                                            |                   |
| 9. On "I am a new customer." section, select "Register Account" radio button (if not selected) and click on "Continue" button.                 |                                                                                                                              | The user is on https://automationteststore.com/index.php?rt=account/create .                                                                           |                   |
| 10. Fill "First Name", "Last Name", "Address 1", "City", "ZIP Code", "Login name", "Password" and "Password Confirm" fields with valid values. | First name: <br/>Last Name: <br/>Address 1: <br/>City: <br/>ZIP Code: <br/>Login name: <br/>Password: <br/>Password Confirm: | All the data must be visible inside appropriate fields.                                                                                                |                   |
| 11. Enter the same email in "Email" field.                                                                                                     | Email:                                                                                                                       | Entered email is visible inside "Email" field.                                                                                                         |                   |
| 12. Select "Country" (any).                                                                                                                    |                                                                                                                              | Selected country must be visible on top of "Country" dropdown.                                                                                         |                   |
| 13. Select "Region / State" (any).                                                                                                             |                                                                                                                              | Selected region/state must be visible on top of "Region / State" dropdown.                                                                             |                   |
| 14. Check "Privacy Policy" checkbox.                                                                                                           |                                                                                                                              | "Privacy Policy" checkbox is checked.                                                                                                                  |                   |
| 15. Push "Continue" button.                                                                                                                    |                                                                                                                              | Registration failed, the user is still on https://automationteststore.com/index.php?rt=account/create . The "already used email" prompt has to appear. |                   |

### 1.2.4. Mobile phone tests

#### 1.2.4.1. Test case - 