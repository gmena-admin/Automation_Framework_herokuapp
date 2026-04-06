# Java Selenium Cucumber Automation Framework

## Project Overview

This repository contains a Java automation framework built for testing a Herokuapp application. It combines web UI automation with Selenium and API testing using REST Assured, all orchestrated by Cucumber scenarios and executed through JUnit Platform.

## Key Technologies

- Java 17
- Maven
- Selenium WebDriver 4.41
- Cucumber 7.x
- JUnit Platform 5
- REST Assured 5.4.0
- Allure 2.33.0
- Apache POI for Excel dataset management
- Gson for JSON handling
## Repository Structure

- `pom.xml` - Maven project configuration and dependency management.
- `src/test/java/runner/TestRunner.java` - main Cucumber test suite.
- `src/test/java/steps` - step definition classes for web and API workflows.
- `src/test/java/poms` - Page Object Models for web page interactions.
- `src/test/java/silver` - reusable utility classes for API, payloads, endpoints, and dataset handling.
- `src/test/resources/features` - Cucumber `.feature` files.
- `src/test/resources/api_data` - API catalog, environment settings, payload templates, and dataset Excel files.
- `src/test/resources/xpath_repository.properties` - UI locators repository.
- `src/test/resources/errors_list.properties` - expected error messages for validation.

## Included Test Types

- Web UI tests under `src/test/resources/features/01. WEB`:
  - `Web_Smoke.feature`
  - `Web_E2E.feature`
  - `Web_Login.feature`
  - `Web_SignUp.feature`
- API tests under `src/test/resources/features/02. API`:
  - `API_Sanity copy.feature`
  - `API_ErrorValidation.feature`
  - `API_Patch.feature`
  - `API_Delete.feature`
  - `API_GET.feature`
- Post-conditions cleanup under `src/test/resources/features/99. PostConditions/`
  - `99. DeleteUser.feature`

## How the Framework Works

- `TestRunner.java` enables execution of the Cucumber test suite using the JUnit Platform suite runner.
- The glue path is configured to `steps`, so all step definitions in `steps.web`, `steps.api`, and `steps.hooks` are discovered.
- Tests use a hook-based browser lifecycle in `steps/hooks/Hooks.java`.
- API tests use `silver.api.REST`, `silver.api.Endpoint`, and `silver.api.RestOperations` to build requests and validate responses.
- Data-driven API payloads and datasets are loaded from `src/test/resources/api_data`.

## Prerequisites

- Java JDK 17 installed. It can be download and installed from here [Adoptium](https://adoptium.net/temurin/releases)
- Maven installed and available in the system path. It can be download and installed from here [Maven](https://maven.apache.org/download.cgi)
- Supported browsers: Chrome, Edge, Firefox.
- Allure CLI installed for report generation, if you want HTML reporting. It can be download and installed from here [Allure Report](https://allurereport.org)

## Running Tests

To download all the necesaries dependencies, the following command should be executed in a terminal:

```powershell
mvn clean install -U
```

It will also run the tests automatically.

### Run all tests

```powershell
mvn test
```

### Run tests by Cucumber tag

```powershell
mvn test "-Dcucumber.filter.tags=@WEB"
```

### Run a specific browser for web tests

Web scenarios use tags such as `@chrome`, `@edge`, and `@firefox` together with `@WEB`.

```powershell
mvn test "-Dcucumber.filter.tags=@WEB and @chrome"
```

#### Include screenshots for web tests

Web scenarios have an option to automatically record the screen for any interaction perform in the website, highlighting the element that the step is interacting with.

```powershell
mvn test "-Dcucumber.filter.tags=@WEB" "-Dscreenshot=true"
```

### Run API scenarios only

```powershell
mvn test "-Dcucumber.filter.tags=@API"
```

### Run post-condition cleanup scenarios

```powershell
mvn test "-Dcucumber.filter.tags=@PostCondition"
```

## Available Cucumber Tags

- `@WEB` - execute web UI tests
- `@API` - execute API tests
- `@chrome`, `@edge`, `@firefox` - select browser for UI tests

> Currently, for a performance boost, all web scenarios are set to be executed using the **Edge** browser.
> To change it, go to the features files and change the tag properly.

## Allure Reporting

Generate a local Allure report from test results:

```powershell
allure generate
allure open
```

Or serve a temporary report:

```powershell
allure serve
```

## API Data and Catalog

- `src/test/resources/api_data/environment.json` - environment definitions and base URLs.
- `src/test/resources/api_data/catalog.json` - catalog of endpoints and operation metadata.
- `src/test/resources/api_data/templates` - request payload templates in JSON.
- `src/test/resources/api_data/datasets` - Excel datasets for data-driven scenarios.

## Browser and Hook Behavior

- `steps/hooks/Hooks.java` initializes the browser driver based on tags.
- UI tests run in headless mode by default.
- Only one browser tag may be selected at a time.
- If no browser tag is specified in the scenarios and/or features, Firefox is used as the default.

## Recommendations for future maintenance

- Keep the API catalog and dataset files synchronized with the test scenarios.
- Add new page objects to `src/test/java/poms` when expanding web flows.
- Use feature files and tags to separate smoke, regression, and validation tests.
- Review `src/test/resources/errors_list.properties` for error message expectations.
