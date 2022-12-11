package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class FlightsAndAirportsInMemoryRepository {

    private List<Flight> addedFlights;

    private Set<Airport> storedAirports;
    private AtomicInteger previousId;


    public FlightsAndAirportsInMemoryRepository() {
        this.addedFlights = new ArrayList<>();
        this.previousId = new AtomicInteger();
        this.storedAirports = new HashSet<>();

    }

    public synchronized List<Flight> getAddedFlights() {
        return addedFlights;
    }

    public void clearFlights() {
        this.addedFlights = new ArrayList<>();
        this.previousId = new AtomicInteger();
        this.storedAirports = new HashSet<>();
    }

    public Set<Airport> getStoredAirports() {
        return storedAirports;
    }

    public AtomicInteger getPreviousId() {
        return previousId;
    }


    public synchronized void saveFlight(Flight flight) {
        this.addedFlights.add(flight);
        this.storedAirports.add(flight.getFrom());
        this.storedAirports.add(flight.getTo());
    }

    public synchronized Flight getFlight(int id) {

        return addedFlights.stream()
                .filter(storedFlight -> storedFlight.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public synchronized void deleteFlight(int id) {
        addedFlights.removeIf(storedFlight -> storedFlight.getId() == id);

    }
}
