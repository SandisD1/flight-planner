package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import io.codelex.flightplanner.flightplan.dto.FlightRequest;
import io.codelex.flightplanner.flightplan.dto.PageResult;
import io.codelex.flightplanner.flightplan.dto.SearchFlightsRequest;


import java.util.List;

public interface FlightPlanService {

    Flight addFlight(FlightRequest flightRequest);

    Flight fetchFlight(Integer id);

    void deleteFlight(Integer id);

    List<Airport> searchAirport(String phrase);

    PageResult searchFlights(SearchFlightsRequest req);

    void clearFlights();
}

