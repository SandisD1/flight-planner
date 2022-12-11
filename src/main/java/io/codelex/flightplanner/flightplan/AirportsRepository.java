package io.codelex.flightplanner.flightplan;

import io.codelex.flightplanner.flightplan.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportsRepository extends JpaRepository<Airport, Integer> {
    @Query("select a from Airport a " +
            "where lower(a.country) like %:keyWord% " +
            "or lower(a.city) like %:keyWord% " +
            "or lower(a.airport) like %:keyWord%")
    List<Airport> findAirportByAnyFieldLike(@Param("keyWord") String keyWord);

}


