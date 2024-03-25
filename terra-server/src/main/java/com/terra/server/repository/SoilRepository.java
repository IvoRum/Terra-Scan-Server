package com.terra.server.repository;

import com.terra.server.model.responce.dto.SoilDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SoilRepository extends BaseRepository {

    private final static String testSoilSql = "select ST_AsText(geom) as polygon, snum as soilNumber, faosoil as soilType" +
            " from soil where country='BULGARIA';";
    private final static String soilInAriaSql = "WITH point AS (" +
            " SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom )" +
            ", buffered_point AS (" +
            "    SELECT ST_Buffer(geom, ?) AS geom" +
            "    FROM point) " +
            "SELECT ST_AsText(s.geom) as polygon, snum as soilNumber, faosoil as soilType " +
            "FROM soil s " +
            "WHERE ST_Intersects(s.geom, (SELECT geom FROM buffered_point));";

    public SoilRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<SoilDTO> getTestSoil() {
        try (ResultSet soilRecords = executeSelectQuery(testSoilSql)) {
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

    public List<SoilDTO> getSoil(final double latitude, final double longitude, final double zoom) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(soilInAriaSql)) {
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.setDouble(3, zoom);

            ResultSet soilRecords = statement.executeQuery();
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

    @Override
    protected List<Double> getParams() {
        return null;
    }
}
