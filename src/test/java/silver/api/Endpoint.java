package silver.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import silver.Utilities.UtilityReader;

/**
 * Represents an API endpoint definition loaded from the catalog.
 *
 * It maps endpoint metadata such as path, method, and payload template file
 * to a runtime Payload object.
 */
public class Endpoint {

    private static final String PAYLOAD_FOLDER = "api_data/templates";

    @SerializedName("endpoint_name")
    private String name;

    @SerializedName("endpoint_path")
    private String path;

    @SerializedName("endpoint_method")
    private String method;

    @SerializedName("endpoint_payload_file")
    private String payloadFile;

    private Payload payload;

    private static final Map<String, Class<? extends Payload>> PAYLOAD_TYPES = new HashMap<>();

    static {
        loadPayloadTypes();
    }

    /**
     * Loads available payload classes for JSON templates in the payload folder.
     */
    private static void loadPayloadTypes() {
        URL resource = Endpoint.class.getClassLoader().getResource(PAYLOAD_FOLDER);
        if (resource == null) {
            return;
        }

        try {
            Path dir = Paths.get(resource.toURI());
            try (Stream<Path> files = Files.walk(dir)) {
                files.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".json"))
                        .forEach(path -> {
                            String relativePath = dir.relativize(path).toString().replace('\\', '/');
                            String className = path.getFileName().toString();
                            className = className.substring(0, className.lastIndexOf('.'));
                            String fullClassName = "api.payloads." + className;
                            try {
                                Class<?> clazz = Class.forName(fullClassName);
                                if (Payload.class.isAssignableFrom(clazz)) {
                                    PAYLOAD_TYPES.put(relativePath, clazz.asSubclass(Payload.class));
                                }
                            } catch (ClassNotFoundException e) {
                                Assertions.fail(
                                        "The following payload is not assigned to any existing class:\nFilename: <"
                                                + relativePath + ">"
                                                + "\nClassName: <" + className + ">\nfullClassName: <" + fullClassName
                                                + ">");
                            }
                        });
            }
        } catch (Exception e) {
            Assertions.fail("No payloads were found in <" + PAYLOAD_FOLDER + ">");
        }
    }

    /**
     * Constructs an empty endpoint to be populated from the JSON catalog.
     */
    public Endpoint() {
        name = "";
        path = "";
        method = "";
        payloadFile = "";
        payload = null;
    }

    /**
     * Returns the endpoint operation name.
     *
     * @return the endpoint name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the endpoint operation name.
     *
     * @param name the endpoint name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the endpoint path.
     *
     * @return the endpoint path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the endpoint path.
     *
     * @param path the endpoint path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the HTTP method for the endpoint.
     *
     * @return the HTTP method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the HTTP method for the endpoint.
     *
     * @param method the HTTP method to use
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Returns the configured payload file name.
     *
     * @return the payload file path relative to `api_data/templates/`
     */
    public String getPayloadFile() {
        return payloadFile;
    }

    /**
     * Sets the payload file and loads the corresponding payload object.
     *
     * @param payloadFile the JSON payload template file name
     */
    public void setPayloadFile(String payloadFile) {
        this.payloadFile = payloadFile;
        loadPayload();
    }

    /**
     * Sets the payload object directly.
     *
     * @param payload the payload instance to use
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * Returns the currently loaded payload object.
     *
     * @return the payload instance, or null when no payload is configured
     */
    public Payload getPayload() {
        return this.payload;
    }

    /**
     * Loads the payload object from the configured payload template file.
     */
    public void loadPayload() {
        if (this.payloadFile == null || this.payloadFile.isEmpty()) {
            this.payload = null;
            return;
        }

        String normalizedPayloadFile = this.payloadFile.replace('\\', '/');
        if (normalizedPayloadFile.startsWith(PAYLOAD_FOLDER + "/")) {
            normalizedPayloadFile = normalizedPayloadFile.substring(PAYLOAD_FOLDER.length() + 1);
        }

        Class<? extends Payload> clazz = PAYLOAD_TYPES.get(normalizedPayloadFile);
        if (clazz == null) {
            Assertions.fail("Unknown payload class for file: " + this.payloadFile);
            return;
        }

        Gson gson = new Gson();
        try (InputStream is = Endpoint.class.getClassLoader()
                .getResourceAsStream(PAYLOAD_FOLDER + "/" + normalizedPayloadFile)) {
            if (is == null) {
                Assertions.fail("Resource not found: " + PAYLOAD_FOLDER + "/" + normalizedPayloadFile);
                return;
            }

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            this.payload = gson.fromJson(reader, clazz);

        } catch (IOException e) {
            Assertions.fail("Error reading payload resource: " + this.payloadFile, e);
        }
    }

    @Override
    public String toString() {

        if (payload != null)
            return "Endpoint [name=" + name + ", path=" + path + ", method=" + method + ", payloadFile=" + payloadFile
                    + ", payload=" + payload.toJson() + "]";

        return "Endpoint [name=" + name + ", path=" + path + ", method=" + method + ", payloadFile=" + payloadFile
                + ", payload=" + null + "]";
    }

    /**
     * Loads dataset values into the payload and substitutes path variables.
     *
     * @param datasetFileName the Excel dataset file name
     * @param rowNumber       the 1-based row number to read
     * @param payload         the payload instance to populate
     * @return the updated payload instance
     */
    public Payload setValuesInPayload(String datasetFileName, int rowNumber, Payload payload) {
        Map<String, String> valuesInRow = UtilityReader.readValuesFromDataSet(datasetFileName, rowNumber);

        for (String key : valuesInRow.keySet()) {
            if (key.equals("_id")) {
                String id = UtilityReader.getValueFromRow(datasetFileName, rowNumber, key);
                path = path.replaceAll("\\{contactID\\}", id);
            }
            if (payload != null)
                payload.setValue(key, valuesInRow.get(key));
        }

        return payload;
    }

}
