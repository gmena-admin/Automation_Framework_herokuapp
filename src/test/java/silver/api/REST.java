package silver.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;

/**
 * Helper class for API environment configuration, base URL resolution, and catalog loading.
 */
public class REST {

    private String baseURL;
    private String environmentName;
    private Catalog catalog;

    /**
     * Initializes REST services for a given environment and catalog.
     *
     * @param environment the target environment name defined in `environment.json`
     * @param catalogName the catalog name defined in `catalog.json`
     */
    public REST(String environment, String catalogName) {

        this.environmentName = searchAndSetName(environment);
        this.baseURL = searchAndSetBaseURL();
        this.catalog = searchAndSetCatalog(catalogName);

        for (Endpoint endpointFromList : catalog.getListEndpoint()) {
            endpointFromList.loadPayload();
        }

    }

    /**
     * Reads the environment name from the environment configuration file.
     *
     * @param environmentStr the requested environment name
     * @return the resolved environment name, or null if not found
     */
    private String searchAndSetName(String environmentStr) {

        Gson gson = new Gson();
        try (InputStream is = REST.class.getClassLoader().getResourceAsStream("api_data/environment.json")) {

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

            Environment[] listEnvironment = gson.fromJson(reader, Environment[].class);

            for (Environment environment : listEnvironment) {
                if (environment.getNameEnv().equals(environmentStr))
                    return environment.getNameEnv();
            }
        } catch (IOException e) {
            Assertions.fail("The file <resources/api_data/environment.json> is not found");
        }

        return null;

    }

    /**
     * Resolves the base URL from the selected environment configuration.
     *
     * @return the base URL for the current environment
     */
    private String searchAndSetBaseURL() {

        Gson gson = new Gson();
        try (InputStream is = REST.class.getClassLoader().getResourceAsStream("api_data/environment.json")) {

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

            Environment[] listEnvironment = gson.fromJson(reader, Environment[].class);

            for (Environment environment : listEnvironment) {
                if (environment.getNameEnv().equals(environmentName))
                    return environment.getBaseUrl();
            }
        } catch (IOException e) {
            Assertions.fail("The file <resources/api_data/environment.json> is not found");
        }

        return null;
    }

    /**
     * Loads the requested API catalog definition from the project resources.
     *
     * @param catalogName the catalog name defined in `catalog.json`
     * @return the matching Catalog object or null if not found
     */
    private Catalog searchAndSetCatalog(String catalogName) {
        Gson gson = new Gson();
        try (InputStream is = REST.class.getClassLoader().getResourceAsStream("api_data/catalog.json")) {

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

            Catalog[] listCatalog = gson.fromJson(reader, Catalog[].class);

            for (Catalog catalogFromList : listCatalog) {
                if (catalogFromList.getNameCatalog().equals(catalogName))
                    return catalogFromList;
            }
        } catch (IOException e) {
            Assertions.fail("The file <resources/api_data/catalog.json> is not found");
        }
        return null;

    }

    // Getters & Setters

    /**
     * Returns the base URL for the currently selected environment.
     *
     * @return the API base URL
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * Overrides the base URL at runtime.
     *
     * @param endpoint the new base URL to use
     */
    public void setBaseURL(String endpoint) {
        this.baseURL = endpoint;
    }

    /**
     * Returns the selected environment name.
     *
     * @return the environment name
     */
    public String getEnvironmentName() {
        return environmentName;
    }

    /**
     * Sets the environment name in the runtime REST object.
     *
     * @param environmentName the environment name to set
     */
    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    /**
     * Returns the loaded API catalog.
     *
     * @return the catalog object
     */
    public Catalog getCatalog() {
        return catalog;
    }

    /**
     * Sets the catalog object used by the REST helper.
     *
     * @param catalog the catalog to use
     */
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Finds an endpoint definition by its operation name.
     *
     * @param operationName the endpoint name from the catalog
     * @return the matching Endpoint object
     */
    public Endpoint getEndpoint(String operationName) {
            
        Endpoint endpoint = null;
        for (Endpoint endpointFromList : catalog.getListEndpoint()) {
                if(endpointFromList.getName().equals(operationName))
                    endpoint = endpointFromList;
        }
        if(endpoint == null)
            Assertions.fail("The operation <" + operationName + "> has not been found in the catalog");

        return endpoint;
    }

}
