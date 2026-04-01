@WEB @Login @Sanity @Edge
Feature: Web Test Cases for Login

    Background:
        Given Access to the url "https://thinking-tester-contact-list.herokuapp.com"
        And Assert that the user is in the page


    Scenario Outline: TC01 - LogIn OK
        When Fullfill the information "<Email>" and "<Password>"
        And Log in
        Then The user is logged properly
        And the user is log out
        Examples:
            | Email                    | Password  |
            | MaravillaTest22@test.com | 123456789 |

    Scenario Outline: TC02 - Access SignUp Page with missing fields
        When Fullfill the information "<Email>" and "<Password>"
        And Log in
        Then An error appeared in "Login Screen" --> "<ErrorReason>"
        Examples:
            | Email                    | Password  | ErrorReason                            |
            |                          | 123456789 | loginMissingEmail                      |
            | MaravillaTest22@test.com |           | loginMissingPassword                   |
            |                          |           | loginMissingEmail,loginMissingPassword |

