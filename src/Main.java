import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Admin\\Downloads\\tickets.json";


        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            FlightData flightData = gson.fromJson(reader, FlightData.class);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}