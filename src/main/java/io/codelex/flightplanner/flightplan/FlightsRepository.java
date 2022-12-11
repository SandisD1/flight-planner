package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import io.codelex.flightplanner.flightplan.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flight, Integer> {
    @Query("select f from Flight f where f.from = :from " +
            "and f.to = :to " +
            "and f.carrier = :carrier " +
            "and f.departureTime =:departure " +
            "and f.arrivalTime =:arrival")
    Flight findFlightByAllFields(@Param("from") Airport from,
                                 @Param("to") Airport to,
                                 @Param("carrier") String carrier,
                                 @Param("departure") LocalDateTime departureTime,
                                 @Param("arrival") LocalDateTime arrivalTime);

    @Query("select f from Flight f join f.from fr join f.to t " +
            "where fr.airport = :from " +
            "and t.airport = :to " +
            "and f.departureTime between :departureStart and :departureEnd ")
    List<Flight> findFlightsByRequest(@Param("from") String from,
                                      @Param("to") String to,
                                      @Param("departureStart") LocalDateTime departureTimeStart,
                                      @Param("departureEnd") LocalDateTime departureTimeEnd);

}

