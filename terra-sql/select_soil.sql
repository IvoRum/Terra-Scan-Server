SELECT ST_X(geom) AS longitude, ST_Y(geom) AS latitude
FROM soil
where gid=1;

select * from soil

SELECT ST_X(st_centroid(geom)) AS longitude, ST_Y(st_centroid(geom)) AS latitude
FROM soil;

select st_area(geom) from soil

SELECT (ST_DumpPoints(geom)).geom AS point_geom
FROM soil;

SELECT
    ST_X((ST_DumpPoints(geom)).geom) AS longitude,
    ST_Y((ST_DumpPoints(geom)).geom) AS latitude
FROM
    soil;

select ST_AsText(geom) from soil;

select st_area(geom) from soil;

SELECT
ST_X(ST_Centroid(geom)),
ST_Y(ST_Centroid(geom)),
ST_X(ST_Centroid(ST_Transform(geom, 4326))) AS long,
ST_Y(ST_Centroid(ST_Transform(geom, 4326))) AS lat
FROM soil;

SELECT (ST_DumpPoints(geom)).path as path,ST_AsEWKT(geom).geom) from soil