package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ApplicationScope
public class FlightPlanRepository {

    private List<Flight> requests;
    private final AtomicInteger idsGiven;

    public FlightPlanRepository() {
        this.requests = new ArrayList<>();
        this.idsGiven = new AtomicInteger();
    }

    public List<Flight> getPlan() {
        return requests;
    }

    public void setPlan(List<Flight> plan) {
        this.requests = plan;
    }

    public AtomicInteger getIdsGiven() {
        return idsGiven;
    }

    public void setIdsGiven(int newIdS) {
        this.idsGiven.set(newIdS);
    }

    public void saveFlight(Flight flight) {
        this.requests.add(flight);
    }

    public Flight getFlight(int id) {

        return requests.stream()
                .filter(storedFlight -> storedFlight.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteFlight(int id) {
        setPlan(requests.stream()
                .filter(storedFlight -> storedFlight.getId() != id)
                .toList());
    }
}
