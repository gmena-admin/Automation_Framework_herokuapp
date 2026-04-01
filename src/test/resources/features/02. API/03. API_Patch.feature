@API @PATCH
Feature: API Patch Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Add Contact (POST) + Update Contact (PATCH) + Delete Contact (DELETE)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_patch.xlsx" in row <RowUpdateId> to include "_id" field from response

        Given I include the information for a "Update Contact (unique properties)" operation using the dataset "dataset_patch.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Delete Contact" operation using the dataset "dataset_patch.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 200

        Examples:
            | RowID | RowUpdateId |
            | 2     | 3           |
            | 2     | 4           |
            | 2     | 5           |
            | 2     | 6           |
            | 2     | 7           |
            | 2     | 8           |
            | 2     | 9           |
            | 2     | 10          |
            | 2     | 11          |
            | 2     | 12          |
            | 2     | 13          |


