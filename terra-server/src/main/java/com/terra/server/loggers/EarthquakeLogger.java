package com.terra.server.loggers;

import com.terra.server.persistence.TerraSearchLogEntity;

import java.sql.Date;
import java.time.Instant;

public class EarthquakeLogger extends TerraLogger {
    private final String Country;

    public EarthquakeLogger(String Country) {
        this.Country = Country;
    }

    public void run() {
        var log = new TerraSearchLogEntity();
        log.setCountry(Country);
        log.setDate(Date.from(Instant.now()));
        log.setSearchType("Earthquake");
        getTemplate().insert(log);
    }
}
