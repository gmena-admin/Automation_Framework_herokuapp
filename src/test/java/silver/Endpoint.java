package silver;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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

    public Endpoint() {
        name = "";
        path = "";
        method = "";
        payloadFile = "";
        payload = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPayloadFile() {
        return payloadFile;
    }

    public void setPayloadFile(String payloadFile) {
        this.payloadFile = payloadFile;
        loadPayload();
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return this.payload;
    }

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
            Assertions.fail("No se conoce la clase para el archivo de payload: " + this.payloadFile);
            return;
        }

        Gson gson = new Gson();
        try (InputStream is = Endpoint.class.getClassLoader()
                .getResourceAsStream(PAYLOAD_FOLDER + "/" + normalizedPayloadFile)) {
            if (is == null) {
                Assertions.fail("No se encontró el recurso: " + PAYLOAD_FOLDER + "/" + normalizedPayloadFile);
                return;
            }

            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            this.payload = gson.fromJson(reader, clazz);

        } catch (IOException e) {
            Assertions.fail("Error leyendo el recurso: " + this.payloadFile, e);
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

    public Payload setValuesInPayload(String datasetFileName, int rowNumber, Payload payload) {
        Map<String, String> valuesInRow = new HashMap<>();
        try {

            URL resource = Endpoint.class.getResource("/api_data/datasets/" + datasetFileName);
            File file = new File(resource.toURI());

            FileInputStream fileinput = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileinput);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            Row valuesRow = sheet.getRow(rowNumber - 1);

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                Cell cellValue = valuesRow.getCell(i);

                String value = cellValue.getStringCellValue();

                valuesInRow.put(headerRow.getCell(i).getStringCellValue(), value);
            }

            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }

        for (String key : valuesInRow.keySet()) {
            payload.setValue(key, valuesInRow.get(key));
        }

        return payload;
    }

}
