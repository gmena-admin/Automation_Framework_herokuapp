package silver.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;

/**
 * Utility class for reading properties, error definitions, JSON payloads,
 * and Excel dataset values from the project resources.
 */
public class UtilityReader {

    /**
     * Reads an XPath locator from the `xpath_repository.properties` file.
     *
     * @param name       the property key to read
     * @param parameters optional replacement values for placeholders like $1, $2
     * @return the resolved XPath string or an empty string if the property is missing
     */
    public String readProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            prop.load(UtilityReader.class.getClassLoader().getResourceAsStream("xpath_repository.properties"));
            xpath = prop.getProperty(name);

            if (parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    xpath = xpath.replace("$" + (i + 1), parameters[i]);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return xpath;
    }

    /**
     * Reads an error message template from the `errors_list.properties` file.
     *
     * @param name       the property key to read
     * @param parameters optional replacement values for placeholders like $1, $2
     * @return the resolved error message template or an empty string if the property is missing
     */
    public String readErrorProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            prop.load(UtilityReader.class.getClassLoader().getResourceAsStream("errors_list.properties"));
            xpath = prop.getProperty(name);

            if (parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    xpath = xpath.replace("$" + (i + 1), parameters[i]);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return xpath;
    }

    /**
     * Reads a JSON payload resource from the classpath.
     *
     * @param filename the name of the payload file inside `src/test/resources/payloads/`
     * @return a JSON string representation of the payload resource
     */
    public String readJSON(String filename) {
        Gson gson = new Gson();

        String payload = UtilityReader.class.getClassLoader().getResourceAsStream("payloads/" + filename).toString();

        return gson.toJson(payload);

    }

    /**
     * Reads a row from an Excel dataset and returns the values mapped by header name.
     *
     * @param datasetFileName the dataset file name in `src/test/resources/api_data/datasets/`
     * @param rowNumber       the 1-based row number to read
     * @return a map of header values and the row cell values
     */
    public static Map<String, String> readValuesFromDataSet(String datasetFileName, int rowNumber) {
        Map<String, String> valuesInRow = new HashMap<>();

        try {

            String resource = System.getProperty("user.dir") + ("/src/test/resources/api_data/datasets/" + datasetFileName);
            File file = new File(resource);

            FileInputStream fileinput = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileinput);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Assertions.assertTrue(rowNumber <= sheet.getPhysicalNumberOfRows(),
                    "The row <" + rowNumber + "> is greater than the total number of rows in dataset <"
                            + sheet.getPhysicalNumberOfRows() + ">");

            Row headerRow = sheet.getRow(0);
            Row valuesRow = sheet.getRow(rowNumber - 1);

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                Cell cellValue = valuesRow.getCell(i);

                String value = cellValue != null && !cellValue.getStringCellValue().isEmpty() ? cellValue.getStringCellValue() : null;

                valuesInRow.put(headerRow.getCell(i).getStringCellValue(), value);
            }

            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return valuesInRow;
    }

    /**
     * Writes a specific value into an Excel dataset row, using a header column name.
     *
     * @param datasetFileName the dataset file name in `src/test/resources/api_data/datasets/`
     * @param rowNumber       the 1-based row number to update
     * @param header          the header name to locate the cell
     * @param valueToWrite    the value to insert into the dataset cell
     */
    public static void writeValuesFromDataSet(String datasetFileName, int rowNumber, String header,
            String valueToWrite) {

        try {

            String resource = System.getProperty("user.dir") + ("/src/test/resources/api_data/datasets/" + datasetFileName);
            File file = new File(resource);

            FileInputStream fileinput = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileinput);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            Row valuesRow = sheet.getRow(rowNumber - 1);

            int cellValueIndex = -1;
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {

                Cell headerCell = headerRow.getCell(i);

                if (headerCell.getStringCellValue().equals(header))
                    cellValueIndex = i;
            }

            Assertions.assertNotEquals(-1, cellValueIndex,
                    "The header <" + header + "> header has not been found in the dataset");

            valuesRow.createCell(cellValueIndex).setCellValue(valueToWrite);

            FileOutputStream fileoutput = new FileOutputStream(file);
            workbook.write(fileoutput);
            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * Reads a specific field value from an Excel dataset row using a header column name.
     *
     * @param datasetFileName the dataset file name in `src/test/resources/api_data/datasets/`
     * @param rowNumber       the 1-based row number to read
     * @param header          the header name for the desired value
     * @return the cell value as a string or null if the cell is empty
     */
    public static String getValueFromRow(String datasetFileName, int rowNumber, String header) {
        String valueFromCell = "";
        try {

            String resource = System.getProperty("user.dir") + ("/src/test/resources/api_data/datasets/" + datasetFileName);
            File file = new File(resource);

            FileInputStream fileinput = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileinput);

            XSSFSheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            Row valuesRow = sheet.getRow(rowNumber - 1);

            int cellValueIndex = -1;
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {

                Cell headerCell = headerRow.getCell(i);

                if (headerCell.getStringCellValue().equals(header))
                    cellValueIndex = i;
            }

            Assertions.assertNotEquals(-1, cellValueIndex,
                    "The header <" + header + "has not been found in the dataset");

            valueFromCell = valuesRow.getCell(cellValueIndex) != null ? valuesRow.getCell(cellValueIndex).getStringCellValue() : null;

            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return valueFromCell;
    }
}
