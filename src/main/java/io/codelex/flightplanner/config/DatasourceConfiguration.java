package io.codelex.flightplanner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "in-database")
@Configuration
public class DatasourceConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "database", havingValue = "h2")
    public DataSource getDatabaseDataSourceH2() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:test")
                .username("sa")
                .password("sa")
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "database", havingValue = "postgres")
    public DataSource getDatabaseDataSourcePostgresql() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/postgres")
                .username("postgres")
                .password("docker")
                .build();
    }
}
