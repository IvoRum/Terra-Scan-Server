package com.terra.server.repository;

import com.terra.server.model.responce.dto.SoilDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SoilRepository extends BaseRepository {

    private final static String sql = "select ST_AsText(geom) as polygon, snum as soilNumber, faosoil as soilType" +
            " from soil where country='BULGARIA';";

    public SoilRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<SoilDTO> getSoil() {
        try (ResultSet soilRecords = executeSelectQuery(sql)) {
            List<SoilDTO> soilDTOS = new ArrayList<>();
            while (soilRecords.next()) {
                SoilDTO current = new SoilDTO();
                var multipolygon = soilRecords.getObject("polygon") == null ? null : soilRecords.getString("polygon");
                current.setCoordinates(parsePolygons(multipolygon));
                current.setSoilNumber(soilRecords.getObject("soilNumber") == null ? null : soilRecords.getInt("soilNumber"));
                current.setSoilType(soilRecords.getObject("soilType") == null ? null : soilRecords.getString("soilType"));
                soilDTOS.add(current);
            }
            soilRecords.close();
            return soilDTOS;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
