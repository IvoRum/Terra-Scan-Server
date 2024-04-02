WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint( -74.3565345928073,40.97529352556944), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 10.0) AS geom
    FROM point
)
SELECT s.dn as magnitude, ST_AsText((ST_DumpRings((ST_Dump(s.geom)).geom)).geom) AS polygon,
       ST_X(ST_Centroid(s.geom)) AS lon, ST_Y(ST_Centroid(s.geom)) AS lat
FROM earthquake s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom);