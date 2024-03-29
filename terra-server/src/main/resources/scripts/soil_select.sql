/*
"WITH point AS ( " +
"    SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom " +
"), " +
"buffered_point AS ( " +
"    SELECT ST_Buffer(geom, ?) AS geom " +
"    FROM point " +
") " +
"SELECT ST_AsText((ST_Dump(s.geom)).geom) AS polygon, s.snum AS soilNumber, s.faosoil AS soilType " +
"FROM soil s " +
"JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom) " +
"WHERE ST_GeometryType(s.geom) = 'ST_MultiPolygon';";

 */