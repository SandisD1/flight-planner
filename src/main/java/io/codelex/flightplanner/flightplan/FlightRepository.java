package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

}
