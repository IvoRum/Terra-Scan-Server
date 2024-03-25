package com.terra.server.repository;

import com.terra.server.model.PolygonPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;
@Repository
@RequiredArgsConstructor
public abstract class BaseRepository {

    protected final DataSource dataSource;
    private static final Pattern SPLIT_SPACE_UNICODE_PATTERN = Pattern.compile("\\p{Blank}+", UNICODE_CHARACTER_CLASS);

    protected final List<PolygonPoint> parsePolygons(String input) {
        List<PolygonPoint> polygonPoints = new ArrayList<>();
        String patternString = "\\(([^\\(\\)]+)\\)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String polygon = matcher.group(1);
            String[] parts = polygon.split(",");
            for(String point: parts){
                String[] splitUnicode = SPLIT_SPACE_UNICODE_PATTERN.split(point);
                float lon = Float.parseFloat(splitUnicode[0]);
                float lat = Float.parseFloat(splitUnicode[1]);
                polygonPoints.add(new PolygonPoint(lon,lat));
            }
        }
        return polygonPoints;
    }

    protected final ResultSet executeSelectQuery(final String Query) throws SQLException {
        Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(Query);
            return statement.executeQuery();
    }
}
