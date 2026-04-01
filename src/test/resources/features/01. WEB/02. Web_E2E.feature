@WEB @E2E @Regression @Edge
Feature: Web Test Cases End-to-End

    Background:
        Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
        And Assert that the user is in the page

    Scenario Outline: TC01 - Sign up as a new user and log in
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system
        And I delete my user

        Given Access to "Sign up" to create a new contact
        When Fullfill the information "<FirstName>", "<LastName>", "<Email>" and "<Password>"
        And Submit the user
        Then The user is created properly
        And the user is log out

        When Fullfill the information "<Email>" and "<Password>"
        And Log in
        Then The user is logged properly
        And the user is log out

        Examples:
            | FirstName | LastName | Email                    | Password  |
            | Test      | Test     | MaravillaTest22@test.com | 123456789 |

