@API @WEB
Feature: Delete current user

    Scenario: Delete user
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system
        And I delete my user