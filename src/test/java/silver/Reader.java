package silver;

import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;

public class Reader {

    public String readProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            // load a properties file from class path, inside static method
            prop.load(Reader.class.getClassLoader().getResourceAsStream("xpath_repository.properties"));

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
            prop.load(Reader.class.getClassLoader().getResourceAsStream("errors_addUser.properties"));

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

        String payload = Reader.class.getClassLoader().getResourceAsStream("payloads/" + filename).toString();

        return gson.toJson(payload);

    }
}
