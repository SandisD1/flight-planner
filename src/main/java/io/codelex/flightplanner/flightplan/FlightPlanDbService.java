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

    private final FlightsRepository flightsRepository;
    private final AirportsRepository airportsRepository;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightPlanDbService(FlightsRepository flightsRepository, AirportsRepository airportsRepository) {
        this.flightsRepository = flightsRepository;
        this.airportsRepository = airportsRepository;
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
            this.airportsRepository.save(newFlight.getFrom());
            this.airportsRepository.save(newFlight.getTo());
            return this.flightsRepository.save(newFlight);
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
        Flight duplicateFlight = this.flightsRepository.findFlightByAllFields(flight.getFrom(),
                flight.getTo(),
                flight.getCarrier(),
                flight.getDepartureTime(),
                flight.getArrivalTime());

        if (duplicateFlight != null) {
            return duplicateFlight.equals(flight);
        }
        return false;

    }

    public boolean sameAirport(Flight flight) {

        return flight.getFrom().equals(flight.getTo());
    }

    public boolean validDatesFlight(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());

    }

    @Override
    public Flight fetchFlight(Integer id) {
        Flight found = this.flightsRepository.findById(id).orElse(null);
        if (found == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found;
    }

    @Override
    public void deleteFlight(Integer id) {
        if (this.flightsRepository.existsById(id)) {
            this.flightsRepository.deleteById(id);
        }
    }

    @Override
    public List<Airport> searchAirport(String phrase) {
        String input = phrase.trim().toLowerCase();

        return this.airportsRepository.findAirportByAnyFieldLike(input);

    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest req) {
        if (req.getFrom().equals(req.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Airports");
        } else {

            LocalDate searchDate = LocalDate.parse(req.getDepartureDate());

            List<Flight> foundFlights = this.flightsRepository.findFlightsByRequest(req.getFrom(),
                    req.getTo(), searchDate.atStartOfDay(),
                    searchDate.plusDays(1).atStartOfDay());

            return new PageResult(foundFlights);
        }
    }

    @Override
    public void clearFlights() {
        this.flightsRepository.deleteAll();
    }
}
