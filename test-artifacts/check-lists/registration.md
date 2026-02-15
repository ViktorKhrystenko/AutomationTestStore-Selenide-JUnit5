# Registration

## Personal details

### Name, last name

- test empty 
- test longer than 32 characters (max length)
- test numbers, spaces, special characters (' - should pass)

### Email

- test according to email rules on https://help.xmatters.com/ondemand/trial/valid_email_format.htm and https://en.wikipedia.org/wiki/Email_address#Syntax (there are rules and examples of valid and invalid emails)
- test registering on already used email

### Mobile phone

- too short
- too long
- without country code
- with letters
- with special characters (except '+', it has to pass)
- register on already used mobile phone

### Fax

## Address

### Company

### Address 1 and 2

- too short (less than 3)
- too long (more than 128)
- empty
- test filled Address 2 and empty Address 1
- with numbers and special characters (should pass)
- same value for Address 1 and Address 2 (should fail)

### City

- too short (less than 3)
- too long (more than 128)
- empty
- with special characters (- ' . *space* should pass)
- numbers (should fail)

### Zip/postal code

- too short (less than 3)
- too long (more than 10)
- empty
- with non a-z letters
- with special characters (- *space* should pass)

### Region/State, Country

- empty
- does Region/State drops when change Country (should drop)

## Login details

### Login name

- too short (less than 5)
- too long (more than 64)
- empty
- with non a-z letters (should pass)
- with special characters (. - *space* should pass)

### Password and password confirm

- too short (less than 4)
- too long (more than 20)
- empty password, filled password confirm
- empty password confirm, filled password
- confirmation error

## Newsletter

- radio button, but nothing is chosen by default (bug)

## Privacy policy

- empty (should fail)