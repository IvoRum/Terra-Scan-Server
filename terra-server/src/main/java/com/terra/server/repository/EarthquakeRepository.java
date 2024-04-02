package com.terra.server.repository;

import com.terra.server.model.PolygonPoint;
import com.terra.server.model.responce.dto.EarthquakeDTO;
import com.terra.server.model.responce.dto.SoilDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class EarthquakeRepository extends BaseRepository {
    private final static String sql = "select ST_AsText(geom) as polygon, dn as magnitude from earthquake vt";
    private final static String sqlPoit="" +
            "WITH point AS ( " +
            "    SELECT ST_SetSRID(ST_MakePoint( ?,?), 4326) AS geom " +
            "), " +
            "buffered_point AS ( " +
            "    SELECT ST_Buffer(geom, ?) AS geom " +
            "    FROM point " +
            ") " +
            "SELECT s.dn as magnitude, ST_AsText((ST_DumpRings((ST_Dump(s.geom)).geom)).geom) AS polygon, " +
            "       ST_X(ST_Centroid(s.geom)) AS lon, ST_Y(ST_Centroid(s.geom)) AS lat " +
            "FROM earthquake s " +
            "JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom);";

    public EarthquakeRepository(DataSource dataSource) {
        super(dataSource);
    }

    private List<Double> sqlParams;

    public List<EarthquakeDTO> getEarthquakes(final double latitude, final double longitude, final double zoom) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(sqlPoit)) {
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.setDouble(3, zoom);

            ResultSet earthquakes = statement.executeQuery();
            List<EarthquakeDTO> earthquakeDTOS = new ArrayList<>();
            while (earthquakes.next()) {
                EarthquakeDTO current = new EarthquakeDTO();
                current.setMagnitude(earthquakes.getObject("magnitude") == null ? null : earthquakes.getInt("magnitude"));
                var multipolygon = earthquakes.getObject("polygon") == null ? null : earthquakes.getString("polygon");
                current.setCoordinates(parsePolygons(multipolygon));
                earthquakeDTOS.add(current);
            }
            earthquakes.close();
            return earthquakeDTOS;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<EarthquakeDTO> getEarthquakesTest(Double... params){
        sqlParams = Arrays.asList(params);
       try ( ResultSet earthquakes = executeSelectQuery(sql)) {
           List<EarthquakeDTO> earthquakeDTOS = new ArrayList<>();
           while (earthquakes.next()) {
               EarthquakeDTO current = new EarthquakeDTO();
               current.setMagnitude(earthquakes.getObject("magnitude") == null ? null : earthquakes.getInt("magnitude"));
               var multipolygon = earthquakes.getObject("polygon") == null ? null : earthquakes.getString("polygon");
               current.setCoordinates(parsePolygons(multipolygon));
               earthquakeDTOS.add(current);
           }
           earthquakes.close();
           return earthquakeDTOS;
       } catch (SQLException e){
           e.printStackTrace();
           throw new RuntimeException();
       }
    }

    @Override
    protected List<Double> getParams() {
        return sqlParams;
    }
}
