import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Admin\\Downloads\\tickets.json";

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();

            FlightData flightData = gson.fromJson(reader, FlightData.class);

            Map<String, String> minTimeBetweenCities = flightData.getMinTimeBetweenCities();

            for (Map.Entry<String, String> entry : minTimeBetweenCities.entrySet()) {
                System.out.println("Авиаперевозчик: " + entry.getKey());
                System.out.println("Минимальное время полета: " + entry.getValue());
                System.out.println();
            }

            System.out.println("Разница между средней ценой и медианой: " + flightData.getDiffBetweenAvgAndMedian());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}