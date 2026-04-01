package silver.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;

public class REST {

    private String baseURL;
    private String environmentName;
    private Catalog catalog;

    public REST(String environment, String catalogName) {

        this.environmentName = searchAndSetName(environment);
        this.baseURL = searchAndSetBaseURL();
        this.catalog = searchAndSetCatalog(catalogName);

        for (Endpoint endpointFromList : catalog.getListEndpoint()) {
            endpointFromList.loadPayload();
        }

    }

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

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String endpoint) {
        this.baseURL = endpoint;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

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
