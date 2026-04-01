@API @Sanity
Feature: API Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Add Contact (POST)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response
        And Update dataset "dataset_patch.xlsx" in row <RowID> to include "_id" field from response
        Examples:
            | RowID |
            | 2     |

    Scenario Outline: Retrieve Contact (GET)
        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 2     |

    Scenario Outline: Update Contact (PUT)
        Given I changed the field(s) "<HeaderName>" in the dataset "dataset.xlsx" for row <RowID> to have value(s) "<NewValue>"
        Given I include the information for a "Update Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID | HeaderName         | NewValue      |
            | 2     | firstName          | Bob           |
            | 2     | firstName,lastName | Mark,O'Connor |

    Scenario Outline: Update Contact (PATCH)
        Given I include the information for a "Update Contact (unique properties)" operation using the dataset "dataset_patch.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 2     |

    Scenario Outline: Retrieve Contact List (GET)
        Given I include the information for a "Get Contact List" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 2     |

    Scenario Outline: Delete Contact and Get Contact
        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404
        Examples:
            | RowID |
            | 2     |