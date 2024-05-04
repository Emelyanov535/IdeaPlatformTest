import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightData {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Map<String, String> getMinTimeBetweenCities() {
        return tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                .collect(Collectors.groupingBy(
                        Ticket::getCarrier,
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparing(this::calculateFlightDuration)),
                                minTicket -> minTicket.map(this::calculateFlightDuration)
                                        .map(this::formatDuration)
                                        .orElse("")
                        )
                ));
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return hours + " ч " + minutes + " мин";
    }

    private Duration calculateFlightDuration(Ticket ticket) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm"); // Используем H:mm для одиночного часа
        LocalDateTime departureDateTime = LocalDateTime.parse(ticket.getDeparture_date() + " " + ticket.getDeparture_time(), dateTimeFormatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse(ticket.getArrival_date() + " " + ticket.getArrival_time(), dateTimeFormatter);
        return Duration.between(departureDateTime, arrivalDateTime);
    }

    public double getDiffBetweenAvgAndMedian(){
        List<Ticket> vvoToTlvTickets = tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                .toList();

        List<Integer> prices = vvoToTlvTickets.stream()
                .map(Ticket::getPrice)
                .toList();

        double averagePrice = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        double medianPrice = calculateMedian(prices);

        return averagePrice - medianPrice;
    }

    private double calculateMedian(List<Integer> prices) {
        List<Integer> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Integer::compareTo);

        int size = sortedPrices.size();
        if (size % 2 == 1) {
            return sortedPrices.get(size / 2);
        } else {
            int midIndex1 = size / 2 - 1;
            int midIndex2 = size / 2;
            return (sortedPrices.get(midIndex1) + sortedPrices.get(midIndex2)) / 2.0;
        }
    }
}
