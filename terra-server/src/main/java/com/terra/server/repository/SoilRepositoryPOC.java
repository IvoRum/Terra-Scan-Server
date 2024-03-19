package com.terra.server.repository;

import com.terra.server.model.responce.SoilPointDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

@Repository
@RequiredArgsConstructor
public class SoilRepositoryPOC {
    private final DataSource dataSource;
    static final Pattern SPLIT_SPACE_UNICODE_PATTERN = Pattern.compile("\\p{Blank}+", UNICODE_CHARACTER_CLASS);


    public List<SoilPointDTO> getSoil() {
        String sql = "select ST_AsText(geom) as polygon from soil " +
                "where country='BULGARIA';";
        Connection connection = DataSourceUtils.getConnection(dataSource);
        String multypoligon="";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            result.next();
            multypoligon = result.getObject("polygon") == null ? null : result.getString("polygon");

            return parsePolygons(multypoligon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> parceMultuPolygon(String multipolygon) {
        String patternString = "MULTIPOLYGON\\(\\(\\(([\\d.]+ [\\d.]+(?:,|\\s)+)+\\)\\)\\)\n";
        String result = multipolygon.replaceFirst("^MULTIPOLYGON", "");
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(multipolygon);

        List<String> polygons=new ArrayList<>();
        while (matcher.find()) {
            polygons.add( matcher.group());
        }
        return polygons;
    }
    public static List<SoilPointDTO> parsePolygons(String input) {
        List<SoilPointDTO> soilPoints=new ArrayList<>();
        String patternString = "\\(([^\\(\\)]+)\\)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String polygon = matcher.group(1);
            String[] parts = polygon.split(",");
            for(String point: parts){
                String[] longAndLat = polygon.split("\\s+");
                String[] splitUnicode = SPLIT_SPACE_UNICODE_PATTERN.split(point);
                float lon = Float.parseFloat(splitUnicode[0]);
                float lat = Float.parseFloat(splitUnicode[1]);
                soilPoints.add(new SoilPointDTO(lon,lat));
            }
        }
        return soilPoints;
    }
}
