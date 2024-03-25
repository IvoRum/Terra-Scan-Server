package com.terra.server.repository;

import com.terra.server.model.responce.dto.EarthquakeDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EarthquakeRepository extends BaseRepository {
    private final static String sql = "select ST_AsText(geom) as polygon, dn as magnitude from earthquake limit 10;";

    public EarthquakeRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<EarthquakeDTO> getEarthquakes(){
       try ( ResultSet earthquakes = executeSelectQuery(sql)) {
           List<EarthquakeDTO> earthquakeDTOS = new ArrayList<>();
           while (earthquakes.next()) {
               EarthquakeDTO current = new EarthquakeDTO();
               current.setMagnitude(earthquakes.getObject("magnitude") == null ? null : earthquakes.getInt("magnitude"));
               var multipolygon = earthquakes.getObject("polygon") == null ? null : earthquakes.getString("polygon");
               current.setCoordinates(parsePolygons(multipolygon));
           }
           earthquakes.close();
           return earthquakeDTOS;
       } catch (SQLException e){
           throw new RuntimeException();
       }
    }
}
