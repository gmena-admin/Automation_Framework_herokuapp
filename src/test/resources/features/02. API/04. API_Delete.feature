@API @DELETE
Feature: API Delete Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Add Contact (POST) + Delete Contact (DELETE) + Delete Contact (DELETE)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404

        Examples:
            | RowID | 
            | 2     | 

    Scenario Outline: Add Contact (POST) + Delete Contact (DELETE) + Retrieve Contact (GET)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404

        Examples:
            | RowID | 
            | 2     |


    Scenario Outline: Add Contact (POST) + Delete Contact (DELETE) + Update Contact (PUT)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Update Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404

        Examples:
            | RowID | 
            | 2     |

    Scenario Outline: Add Contact (POST) + Delete Contact (DELETE) + Update Contact (PATCH)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_patch.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Delete Contact" operation using the dataset "dataset_patch.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Update Contact (unique properties)" operation using the dataset "dataset_patch.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 404

        Examples:
            | RowID | 
            | 2     |
