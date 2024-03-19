package com.terra.server.repository;

import com.terra.server.model.responce.EarthquakeDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EarthquakeRepository {

    private final DataSource dataSource;

    public EarthquakeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<List<EarthquakeDTO>> getEarthquakes() throws SQLException {
        String sql="select ST_AsText(geom), * from earthquake " +
                "limit 10;";
        Connection connection = DataSourceUtils.getConnection(dataSource);
        ArrayList<EarthquakeDTO> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                EarthquakeDTO dto = new EarthquakeDTO();
                dto.setTest(resultSet.getInt(0));
                result.add(dto);
            }
        }
        return Optional.of(result);
    }
}
