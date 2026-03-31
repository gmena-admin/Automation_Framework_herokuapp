@API
Feature: API Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Add Contact
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" to include "_id" field from response
        Examples:
            | RowID |
            | 2     |
            | 3     |

    Scenario Outline: Get Contact
        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 3     |

    Scenario Outline: Delete Contact and Get Contact
        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404
        Examples:
            | RowID |
            | 3     |

    @MANUAL
    Scenario Outline: Manual Delete Contact
        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 2     |
            | 3     |
            | 4     |
            | 5     |
           