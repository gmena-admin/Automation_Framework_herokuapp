@WEB @SignUp
Feature: Web Test Cases for SignUp

    Background:
        Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
        And Assert that the user is in the page

    Scenario Outline: TC01 - Sign up as a new user
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then The user is created properly
        And the user is log out
        Examples:
            | FirstName | LastName | Email                     | Password  |
            | Test      | Test     | MaaravillaTest22@test.com | 123456789 |


    Scenario Outline: TC02 - Sign up as a new user with missing fields
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then An error appeared in "SignUp Screen" --> "<ErrorReason>"
        Examples:
            | FirstName | LastName | Email                    | Password  | ErrorReason                                                                           |
            |           | Test     | MaravillaTest22@test.com | 123456789 | signupMissingFirstName                                                                |
            | Test      |          | MaravillaTest22@test.com | 123456789 | signupMissingLastName                                                                 |
            | Test      | Test     |                          | 123456789 | signupMissingEmail                                                                    |
            | Test      | Test     | MaravillaTest22@test.com |           | signupMissingPassword                                                                 |
            |           |          | MaravillaTest22@test.com | 123456789 | signupMissingFirstName,signupMissingLastName                                          |
            |           |          |                          | 123456789 | signupMissingFirstName,signupMissingLastName,signupMissingEmail                       |
            |           |          |                          |           | signupMissingFirstName,signupMissingLastName,signupMissingEmail,signupMissingPassword |

    Scenario Outline: TC03 - Sign up as a new user with wrong fields values
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then An error appeared in "SignUp Screen" --> "<ErrorReason>"
        Examples:
            | FirstName | LastName | Email                    | Password  | ErrorReason         |
            | Test      | Test     | MaravillaTest22          | 123456789 | wrongEmailFormat    |
            | Test      | Test     | MaravillaTest22@test.com | 1         | wrongPasswordLength |
            | Test      | Test     | MaravillaTest22@test.com | 123456789 | sameUser            |
