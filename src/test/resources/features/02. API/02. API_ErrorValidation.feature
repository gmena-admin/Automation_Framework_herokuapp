@API
Feature: API Error Validations Test Cases

    Background:
        Given The API tests are running under "PRO" environment and using the catalog "Contacts"
        And I login into the system

    Scenario Outline: Add Contact (POST) missing mandatory fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 400
        And With the data in dataset "dataset_errorValidation.xlsx" for row <RowID>, the reason is "<Reason>"
        Examples:
            | RowID | Reason                      |
            | 2     | addContact_MissingFirstName |
            | 3     | addContact_MissingLastName  |

    Scenario Outline: Add Contact (POST) missing non-mandatory fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_errorValidation.xlsx" in row <RowID> to include "_id" field from response


        Given I include the information for a "Delete Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID |
            | 4     |
            | 5     |
            | 6     |
            | 7     |
            | 8     |
            | 9     |
            | 10    |
            | 11    |
            | 12    |
            | 13    |


    Scenario Outline: Add Contact (POST) wrong value fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 400
        And With the data in dataset "dataset_errorValidation.xlsx" for row <RowID>, the reason is "<Reason>"
        Examples:
            | RowID | Reason                                                                                                                        |
            | 14    | addContact_wrongBirthDate                                                                                                     |
            | 15    | addContact_wrongEmail                                                                                                         |
            | 16    | addContact_wrongPhone                                                                                                         |
            | 17    | addContact_longerPostalCode                                                                                                   |
            | 18    | addContact_wrongPostalCode                                                                                                    |
            | 19    | addContact_wrongBirthDate,addContact_longerPostalCode                                                                         |
            | 20    | addContact_wrongBirthDate,addContact_wrongEmail,addContact_wrongPhone,addContact_longerPostalCode                             |
            | 21    | addContact_MissingFirstName,addContact_wrongBirthDate,addContact_wrongEmail,addContact_wrongPhone,addContact_longerPostalCode |


    #@Errors
    Scenario Outline: Update Contact (PUT) missing mandatory fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_errorValidation.xlsx" in row <RowUpdateId> to include "_id" field from response

        Given I include the information for a "Update Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 400
        And With the data in dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>, the reason is "<Reason>"

        Given I include the information for a "Delete Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID | RowUpdateId | Reason                      |
            | 2     | 2           | addContact_MissingFirstName |
            | 2     | 3           | addContact_MissingLastName  |

    #@Errors
    Scenario Outline: Update Contact (PUT) wrong value fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_errorValidation.xlsx" in row <RowUpdateId> to include "_id" field from response

        Given I include the information for a "Update Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 400
        And With the data in dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>, the reason is "<Reason>"

        Given I include the information for a "Delete Contact" operation using the dataset "dataset_errorValidation.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID | RowUpdateId | Reason                                                                                                                        |
            | 2     | 14          | addContact_wrongBirthDate                                                                                                     |
            | 2     | 15          | addContact_wrongEmail                                                                                                         |
            | 2     | 16          | addContact_wrongPhone                                                                                                         |
            | 2     | 17          | addContact_longerPostalCode                                                                                                   |
            | 2     | 18          | addContact_wrongPostalCode                                                                                                    |
            | 2     | 19          | addContact_wrongBirthDate,addContact_longerPostalCode                                                                         |
            | 2     | 20          | addContact_wrongBirthDate,addContact_wrongEmail,addContact_wrongPhone,addContact_longerPostalCode                             |
            | 2     | 21          | addContact_MissingFirstName,addContact_wrongBirthDate,addContact_wrongEmail,addContact_wrongPhone,addContact_longerPostalCode |

    @Errors
    Scenario Outline: Update Contact (PATCH) wrong value fields
        Given I include the information for a "Add Contact" operation using the dataset "dataset.xlsx" for row <RowID>
        When I send the operation
        Then The status code is 201
        And Update dataset "dataset_patch.xlsx" in row <RowUpdateId> to include "_id" field from response

        Given I include the information for a "Update Contact (unique properties)" operation using the dataset "dataset_patch.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 400
        And With the data in dataset "dataset_patch.xlsx" for row <RowUpdateId>, the reason is "<Reason>"

        Given I include the information for a "Delete Contact" operation using the dataset "dataset_patch.xlsx" for row <RowUpdateId>
        When I send the operation
        Then The status code is 200
        Examples:
            | RowID | RowUpdateId | Reason                      |
            | 2     | 15          | addContact_wrongBirthDate   |
            | 2     | 16          | addContact_wrongEmail       |
            | 2     | 17          | addContact_longerPhone       |
            | 2     | 18          | addContact_longerPostalCode |
            | 2     | 19          | addContact_wrongPostalCode  |



