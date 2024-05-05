import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "/tickets.json";

        try (InputStream inputStream = Main.class.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                System.out.println("Файл " + filePath + " не найден внутри JAR-файла.");
                return;
            }

            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");

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
