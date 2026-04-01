@API @GET
Feature: API Delete Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system


    Scenario Outline: Add Contact (POST) + Retrieve Contact (GET) + Delete Contact (DELETE)
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Get Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        And The response contains the same value in the field "<fieldName>" using the data in the dataset "dataset.xlsx" for row <RowID>

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200


        Examples:
            | RowID | fieldName |
            | 2     | firstName |

    Scenario Outline: Add Contact (POST) + Retrieve Contact List (GET) + Delete Contact (DELETE)

        Given I include the information for a "Get Contact List" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        And I save the number of elements in the response

        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset.xlsx" in row <RowID> to include "_id" field from response

        Given I include the information for a "Get Contact List" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        And The response contains 1 "more" element from the previous call

        Given I include the information for a "Delete Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200

        Given I include the information for a "Get Contact List" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        And The response contains 1 "less" element from the previous call


        Examples:
            | RowID | 
            | 2     | 
