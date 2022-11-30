package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {

}


