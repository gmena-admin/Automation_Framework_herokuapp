@WEB
Feature: Web Test Cases for Smoke Suite

    Background:
        Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
        And Assert that the user is in the page

    Scenario Outline: Sign up as a new user
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then The user is created properly
        And the user is log out
        Examples:
            | FirstName | LastName | Email                    | Password  |
            | Test      | Test     | MaravillaTest22@test.com | 123456789 |


    Scenario Outline: Sign up as a new user with missing fields
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then An error appeared --> "<ErrorReason>"
        Examples:
            | FirstName | LastName | Email                    | Password  | ErrorReason                                                   |
            |           | Test     | MaravillaTest22@test.com | 123456789 | missingFirstName                                              |
            | Test      |          | MaravillaTest22@test.com | 123456789 | missingLastName                                               |
            | Test      | Test     |                          | 123456789 | missingEmail                                                  |
            | Test      | Test     | MaravillaTest22@test.com |           | missingPassword                                               |
            |           |          | MaravillaTest22@test.com | 123456789 | missingFirstName,missingLastName                              |
            |           |          |                          | 123456789 | missingFirstName,missingLastName,missingEmail                 |
            |           |          |                          |           | missingFirstName,missingLastName,missingEmail,missingPassword |

    Scenario Outline: Sign up as a new user with wrong fields values
        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then An error appeared --> "<ErrorReason>"
        Examples:
            | FirstName | LastName | Email                    | Password  | ErrorReason         |
            | Test      | Test     | MaravillaTest22          | 123456789 | wrongEmailFormat    |
            | Test      | Test     | MaravillaTest22@test.com | 1         | wrongPasswordLength |
            | Test      | Test     | MaravillaTest22@test.com | 123456789 | sameUser            |
