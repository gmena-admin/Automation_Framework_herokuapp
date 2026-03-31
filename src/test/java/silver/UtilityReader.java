package silver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;

import com.google.gson.Gson;

import lombok.val;

public class UtilityReader {

    public String readProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            // load a properties file from class path, inside static method
            prop.load(UtilityReader.class.getClassLoader().getResourceAsStream("xpath_repository.properties"));

            // get the property value and print it out
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

    public String readErrorProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            // load a properties file from class path, inside static method
            prop.load(UtilityReader.class.getClassLoader().getResourceAsStream("errors_addUser.properties"));

            // get the property value and print it out
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

    public String readJSON(String filename) {
        Gson gson = new Gson();

        String payload = UtilityReader.class.getClassLoader().getResourceAsStream("payloads/" + filename).toString();

        return gson.toJson(payload);

    }

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

                String value = cellValue != null ? cellValue.getStringCellValue() : null;

                valuesInRow.put(headerRow.getCell(i).getStringCellValue(), value);
            }

            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return valuesInRow;
    }

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
                    "The header <" + header + "has not been found in the dataset");

            valuesRow.createCell(cellValueIndex).setCellValue(valueToWrite);

            FileOutputStream fileoutput = new FileOutputStream(file);
            workbook.write(fileoutput);
            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

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

            valueFromCell = valuesRow.getCell(cellValueIndex) != null ? valuesRow.getCell(cellValueIndex).getStringCellValue() : "";

            workbook.close();

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        return valueFromCell;
    }
}
