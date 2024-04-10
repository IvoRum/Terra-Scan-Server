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

    private static final String WithClause = "WITH point AS (" +
            "    SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom" +
            ")" +
            ", buffered_point AS (" +
            "    SELECT ST_Buffer(geom, ?) AS geom" +
            "    FROM point" +
            ")";
    private static final String WhereClause =  " WHERE ST_Intersects(vt.geom, (SELECT geom FROM buffered_point));";

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
            PreparedStatement statement = connection.prepareStatement(WithClause + Query + WhereClause);
            int paramIndex = 1;
            for(Double param : getParams()){
                statement.setObject(paramIndex++, param);
            }
            return statement.executeQuery();
    }

    protected final ResultSet executeSimpleSelectQuery(final String Query) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(Query);
        return statement.executeQuery();
    }

    protected abstract List<Double> getParams();

    public String getCountryOfPoint(double Latitude, double Longitude) {
        String query =
                "WITH point AS (  SELECT ST_SetSRID(ST_MakePoint(?,?), 4326) AS geom ) " +
                        " SELECT s.country as country FROM soil s JOIN point p ON ST_Intersects(s.geom, p.geom);";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, Latitude);
            statement.setDouble(2, Longitude);
            ResultSet resultSet = statement.executeQuery();
            String country = null;
            if(resultSet.next()){
                country = resultSet.getString("country");
            }
            resultSet.close();
            statement.close();
            return country;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
