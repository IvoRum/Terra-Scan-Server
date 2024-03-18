package com.terra.server.repository;

import com.terra.server.model.responce.SoilDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class SoilRepositoryPOC {
    private final DataSource dataSource;

    public List<String> getSoil() {
        String sql = "select ST_AsText(geom) as polygon from soil " +
                "where country='BULGARIA';";
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String multypoligon="";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            result.next();
            multypoligon = result.getObject("polygon") == null ? null : result.getString("polygon");
            return parceMultuPolygon(multypoligon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> parceMultuPolygon(String multipolygon) {
        String patternString = "MULTIPOLYGON\\(\\(\\(([\\d.]+ [\\d.]+(?:,|\\s)+)+\\)\\)\\)\n";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(multipolygon);

        List<String> polygons=new ArrayList<>();
        while (matcher.find()) {
            polygons.add( matcher.group());
        }
        return polygons;
    }
}
