@API
Feature: API Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Access endpoint
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        Examples:
            | RowID |
            | 2     |