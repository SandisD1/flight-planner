package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.FlightRequest;
import io.codelex.flightplanner.flightplan.dto.PageResult;
import io.codelex.flightplanner.flightplan.dto.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "in-database")
public class FlightPlanDbService implements FlightPlanService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightPlanDbService(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Flight addFlight(FlightRequest flightRequest) {
        Flight newFlight = parseToFlight(flightRequest);
        if (flightAlreadyAdded(newFlight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't add same flight twice");
        } else if (sameAirport(newFlight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Airports");
        } else if (!validDatesFlight(newFlight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival after departure");
        } else {
            this.airportRepository.save(newFlight.getFrom());
            this.airportRepository.save(newFlight.getTo());
            return this.flightRepository.save(newFlight);
        }
    }

    public Flight parseToFlight(FlightRequest flightRequest) {

        return new Flight(flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                LocalDateTime.parse(flightRequest.getDepartureTime(), dateTimeFormatter),
                LocalDateTime.parse(flightRequest.getArrivalTime(), dateTimeFormatter));

    }

    public boolean flightAlreadyAdded(Flight flight) {

        return this.flightRepository.findAll().stream()
                .anyMatch(storedFlight -> storedFlight.equals(flight));
    }

    public boolean sameAirport(Flight flight) {

        return flight.getFrom().equals(flight.getTo());
    }

    public boolean validDatesFlight(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());

    }

    @Override
    public Flight fetchFlight(Integer id) {
        Flight found = this.flightRepository.findById(id).orElse(null);
        if (found == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found;
    }

    @Override
    public void deleteFlight(Integer id) {
        if (this.flightRepository.existsById(id)) {
            this.flightRepository.deleteById(id);
        }
    }

    @Override
    public List<Airport> searchAirport(String phrase) {
        String input = phrase.trim().toUpperCase();
        return this.airportRepository.findAll()
                .stream()
                .filter(storedAirport -> searchInAirport(storedAirport, input))
                .toList();
    }

    public boolean searchInAirport(Airport airport, String phrase) {

        return airport.getAirport().toUpperCase().contains(phrase)
                || airport.getCity().toUpperCase().contains(phrase)
                || airport.getCountry().toUpperCase().contains(phrase);
    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest req) {
        if (req.getFrom().equals(req.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Airports");
        } else {
            List<Flight> foundFlights =
                    this.flightRepository.findAll()
                            .stream()
                            .filter(storedFlight -> storedFlight.getFrom()
                                    .getAirport()
                                    .equals(req.getFrom()) &&
                                    storedFlight.getTo()
                                            .getAirport()
                                            .equals(req.getTo()) &&
                                    storedFlight.getDepartureTime()
                                            .toLocalDate()
                                            .equals(LocalDate.parse(req.getDepartureDate())))
                            .toList();

            return new PageResult(foundFlights);
        }
    }

    @Override
    public void clearFlights() {
        this.flightRepository.deleteAll();
    }
}
