package silver;

import java.io.IOException;
import java.util.Properties;

public class XpathReader {

    public String readProperty(String name, String... parameters) {
        String xpath = "";

        Properties prop = new Properties();

        try {
            // load a properties file from class path, inside static method
            prop.load(XpathReader.class.getClassLoader().getResourceAsStream("xpath_repository.properties"));

            // get the property value and print it out
            xpath = prop.getProperty(name);

            if(parameters.length > 0){
                for (int i = 0; i < parameters.length; i++) {
                    xpath = xpath.replace("$"+(i+1), parameters[i]);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return xpath;
    }
}
