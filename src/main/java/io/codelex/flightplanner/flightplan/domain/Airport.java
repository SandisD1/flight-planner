package io.codelex.flightplanner.flightplan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "airport")
public class Airport {
    @NotBlank
    @Column(name = "country")
    private String country;
    @NotBlank
    @Column(name = "city")
    private String city;
    @NotBlank
    @Id
    @Column(name = "airport")
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country.trim();
        this.city = city.trim();
        this.airport = airport.trim();
    }

    public Airport() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.trim();
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport.trim();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country) && city.equalsIgnoreCase(airport1.city) && airport.equalsIgnoreCase(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }
}
