import java.io.File;  // Import the File class
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class SimulationConfigHandler {
    String filename="config";
    File file;
    ObjectMapper objectmapper;
    SimulationConfig config;

    public void read() throws IOException {
        // Loading the YAML file from the project root folder
        File file = new File(filename + ".yaml");
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        this.config = om.readValue(file, SimulationConfig.class);
    }
    public void printFile(){
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
