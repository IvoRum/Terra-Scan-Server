package com.terra.server.repository;

import com.terra.server.model.responce.SoilDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class SoilRepositoryPOC {
    private final DataSource dataSource;

    public Optional<SoilDTO> getSoil(){
        String sql="select ST_AsText(geom), * from soil " +
                "where country='BULGARIA';";
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            

        }
    }
}
