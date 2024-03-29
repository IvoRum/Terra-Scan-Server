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

select * from soil

select ST_AsText(geom), * from soil
where country='BULGARIA'
;

select ST_AsText(geom) as polygon from soil " +
                "where country='BULGARIA';

select st_area(geom) from soil;

SELECT
ST_X(ST_Centroid(geom)),
ST_Y(ST_Centroid(geom)),
ST_X(ST_Centroid(ST_Transform(geom, 4326))) AS long,
ST_Y(ST_Centroid(ST_Transform(geom, 4326))) AS lat
FROM soil;

SELECT (ST_DumpPoints(geom)).path as path,ST_AsEWKT(geom).geom) from soil

SELECT ST_AsText(geom)
FROM soil
WHERE ST_Within(geom, ST_GeomFromText('-34.67598342895508 83.59786987304688', 4326));

WITH soil AS (
    SELECT ST_SetSRID(ST_MakePoint('-34.67598342895508', '83.59786987304688'), 4326) AS geom
)
SELECT ST_Area(ST_Transform(ST_Buffer(p.geom,ST_Area( ST_Transform(ST_Buffer(p.geom, 500))), 3857))
    AS area_in_square_meters
FROM soil p;


-- Assuming you have a table named 'locations' with a column 'geom' of type geometry(Point, 4326)
-- Replace 'your_table_name' with your actual table name
-- Replace 'your_latitude' and 'your_longitude' with the specific latitude and longitude you're interested in
-- Buffer radius is set to 500 meters

WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
)
SELECT ST_Area(ST_Transform(ST_Buffer(p.geom, 500), 3857)) AS area_in_square_meters
FROM point p;


-- Assuming you have a table named 'locations' with a column 'geom' of type geometry(Point, 4326)
-- Replace 'your_table_name' with your actual table name
-- Replace 'your_latitude' and 'your_longitude' with the specific latitude and longitude you're interested in
-- Buffer radius is set to 500 meters

WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
)
SELECT count(ST_AsText(ST_Buffer(s.geom, 6000))) AS buffered_geometry
FROM soil s;

select count(s.geom) from soil s;

/*
    This is the shit tis works
*/
WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
)
, buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT ST_AsText(s.geom) as geom, snum as soilNumber, faosoil as soilType
FROM soil s
WHERE ST_Intersects(s.geom, (SELECT geom FROM buffered_point));


WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT ST_AsText((ST_Dump(s.geom)).geom) AS geom, s.snum AS soilNumber, s.faosoil AS soilType
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom)
WHERE ST_GeometryType(s.geom) = 'ST_MultiPolygon';

/*
    Only polygons
 */

WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT (ST_Dump(s.geom)).geom AS geom, s.snum AS soilNumber, s.faosoil AS soilType
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom)
WHERE  ST_NRings(s.geom) = 1;


select ST_AsText(geom) as polygon, dn as magnitude from earthquake limit 10;

select ST_AsText(geom) as polygon, snum as soilNumber, faosoil as soilType from soil where country='BULGARIA';

/*
    New select of all inner rings
 */

 WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT ST_DumpRings((ST_Dump(s.geom)).geom) AS geom, s.snum AS soilNumber, s.faosoil AS soilType
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom)

/*
    And with geom
 */

    WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT (ST_DumpRings((ST_Dump(s.geom)).geom)).geom AS geom, s.snum AS soilNumber, s.faosoil AS soilType
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom)

WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT s.snum AS soilNumber, s.faosoil AS soilType,(ST_DumpRings((ST_Dump(s.geom)).geom)).geom AS geom
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom);


WITH point AS (
    SELECT ST_SetSRID(ST_MakePoint(-74.0060, 40.7128), 4326) AS geom
),
buffered_point AS (
    SELECT ST_Buffer(geom, 1.0) AS geom
    FROM point
)
SELECT s.snum AS soilNumber, s.faosoil AS soilType,ST_AsText((ST_DumpRings((ST_Dump(s.geom)).geom)).geom) AS geom
FROM soil s
JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom);

SELECT s.snum AS soilNumber, s.faosoil AS soilType, ST_AsText((ST_DumpRings((ST_Dump(s.geom)).geom)).geom) AS polygon
            FROM soil s;



MULTIPOLYGON(((-34.67598342895508 83.59786987304688,-34.649993896484375 83.5938720703125,-34.63611602783203 83.58943176269531,-34.6280517578125 83.58360290527344,-34.584999084472656 83.56915283203125,-34.58222198486328 83.5635986328125,-34.58222198486328 83.55914306640625,-34.57695007324219 83.55276489257812,-34.56889343261719 83.54693603515625,-34.54167175292969 83.54248046875,-34.51222229003906 83.53997802734375,-34.48777770996094 83.54081726074219,-34.47972106933594 83.54275512695312,-34.46333312988281 83.54942321777344,-34.46055603027344 83.55303955078125,-34.46055603027344 83.55747985839844,-34.46305847167969 83.56137084960938,-34.45750427246094 83.56721496582031,-34.45722198486328 83.57777404785156,-34.454444885253906 83.58305358886719,-34.4405517578125 83.58749389648438,-34.410552978515625 83.59414672851562,-34.364166259765625 83.59693908691406,-34.342498779296875 83.59553527832031,-34.326393127441406 83.59332275390625,-34.310279846191406 83.5877685546875,-34.30249786376953 83.58360290527344,-34.30555725097656 83.57693481445312,-34.30583190917969 83.56637573242188,-34.30083465576172 83.55998229980469,-34.29833984375 83.55442810058594,-34.282501220703125 83.54887390136719,-34.255836486816406 83.54443359375,-34.23444366455078 83.53858947753906,-34.22694396972656 83.53276062011719,-34.19499969482422 83.52192687988281,-34.12110900878906 83.50694274902344,-34.08361053466797 83.50166320800781,-34.04389190673828 83.49720764160156,-33.954444885253906 83.48275756835938,-33.93860626220703 83.47886657714844,-33.93638610839844 83.47331237792969,-33.93999481201172 83.46665954589844,-33.93250274658203 83.4608154296875,-33.91722106933594 83.45526123046875,-33.882774353027344 83.45248413085938,-33.85889434814453 83.45359802246094,-33.83721923828125 83.45664978027344,-33.7852783203125 83.46832275390625,-33.760284423828125 83.47526550292969,-33.74888610839844 83.48054504394531,-33.745277404785156 83.48580932617188,-33.760833740234375 83.49136352539062,-33.78417205810547 83.49636840820312,-33.8155517578125 83.50138854980469,-33.9405517578125 83.51388549804688,-34.01416778564453 83.52886962890625,-34.0352783203125 83.53471374511719,-34.07722473144531 83.55081176757812,-34.08167266845703 83.5616455078125,-34.081390380859375 83.56637573242188,-34.06999969482422 83.57026672363281,-34.02861022949219 83.57943725585938,-33.943328857421875 83.58888244628906,-33.89972686767578 83.59248352050781,-33.784446716308594 83.59971618652344,-33.73527526855469 83.60137939453125,-33.68860626220703 83.60415649414062,-33.43666076660156 83.61080932617188,-33.187774658203125 83.61526489257812,-33.14611053466797 83.61665344238281,-33.096107482910156 83.62136840820312,-33.07167053222656 83.62220764160156,-33.0191650390625 83.62303161621094,-32.908050537109375 83.62025451660156,-32.75556182861328 83.61886596679688,-32.631385803222656 83.62303161621094,-32.57917022705078 83.62359619140625,-32.52222442626953 83.62248229980469,-32.398338317871094 83.61665344238281,-32.29528045654297 83.614990234375,-32.233612060546875 83.61192321777344,-32.218231201171875 83.61048126220703,-32.209999084472656 83.60971069335938,-32.203887939453125 83.60386657714844,-32.218055725097656 83.60247802734375,-32.22972106933594 83.59999084472656,-32.27471923828125 83.59664916992188,-32.3013916015625 83.58970642089844,-32.28944396972656 83.58499145507812,-32.28583526611328 83.58027648925781,-32.28889465332031 83.5797119140625,-32.299171447753906 83.57331848144531,-32.30027770996094 83.57026672363281,-32.276947021484375 83.56805419921875,-32.22972106933594 83.57054138183594,-32.1875 83.57499694824219,-32.16444396972656 83.57832336425781,-32.11444854736328 83.58831787109375,-32.08611297607422 83.59109497070312,-31.960830688476562 83.59109497070312,-31.871944427490234 83.59637451171875,-31.809444427490234 83.6019287109375,-31.75222396850586 83.60220336914062,-31.70055389404297 83.5958251953125,-31.653331756591797 83.59136962890625,-31.488052368164062 83.57859802246094,-31.43527603149414 83.57527160644531,-31.261669158935547 83.56944274902344,-31.15416717529297 83.56721496582031,-30.946945190429688 83.56944274902344,-30.844444274902344 83.57304382324219,-30.751392364501953 83.57887268066406,-30.708332061767578 83.58305358886719,-30.691944122314453 83.58749389648438,-30.677776336669922 83.59275817871094,-30.63638687133789 83.59803771972656,-30.59722137451172 83.60026550292969,-30.44277572631836 83.60247802734375,-30.38833236694336 83.60220336914062,-30.33194351196289 83.60081481933594,-30.21500015258789 83.59609985351562,-30.15444564819336 83.59304809570312,-30.09194564819336 83.58859252929688,-30.031391143798828 83.58554077148438,-29.969165802001953 83.58137512207031,-29.91611099243164 83.57916259765625,-29.856945037841797 83.57859802246094,-29.796669006347656 83.57499694824219,-29.699443817138672 83.56694030761719,-29.662220001220703 83.56192016601562,-29.571666717529297 83.55247497558594,-29.520278930664062 83.54914855957031,-29.480552673339844 83.54470825195312,-29.41555404663086 83.54109191894531,-29.259716033935547 83.52953338623047,-29.254722595214844 83.52915954589844,-29.23332977294922 83.5252685546875,-29.213333129882812 83.51971435546875,-29.215831756591797 83.51666259765625,-29.24555206298828 83.50999450683594,-29.258892059326172 83.50387573242188,-29.255558013916016 83.49803161621094,-29.235553741455078 83.49247741699219,-29.19916534423828 83.48776245117188,-29.179443359375 83.48220825195312,-29.157222747802734 83.47998046875,-29.099998474121094 83.47747802734375,-29.05666732788086 83.48165893554688,-29.026947021484375 83.48831176757812,-29.05750274658203 83.50332641601562,-29.02166748046875 83.50721740722656,-28.987415313720703 83.50798797607422,-28.976381301879883 83.48326110839844,-29.019498825073242 83.44536590576172,-29.073226928710938 83.41368865966797,-29.1375675201416 83.38822937011719,-29.242834091186523 83.38761138916016,-29.32676124572754 83.38526916503906,-29.398405075073242 83.39048767089844,-29.470064163208008 83.3941650390625,-29.523435592651367 83.39615631103516,-29.595176696777344 83.3921890258789,-29.67129898071289 83.4066162109375,-29.74615478515625 83.39655303955078,-29.791818618774414 83.4061279296875,-29.8558349609375 83.4112777709961,-29.919963836669922 83.40570831298828,-29.94461441040039 83.38296508789062,-29.933141708374023 83.3139877319336,-29.8709774017334 83.27824401855469,-29.787212371826172 83.2652816772461,-29.69718360900879 83.26756286621094,-29.608566284179688 83.28057098388672,-29.529020309448242 83.30130767822266,-29.460426330566406 83.29612731933594,-29.370479583740234 83.29075622558594,-29.2987060546875 83.29779052734375,-29.216434478759766 83.28789520263672,-29.11577606201172 83.28549194335938,-29.013675689697266 83.27542877197266,-28.961795806884766 83.27650451660156,-28.873310089111328 83.27727508544922,-28.757478713989258 83.26708984375,-28.632545471191406 83.25223541259766,-28.577665328979492 83.24869537353516,-28.492244720458984 83.24795532226562,-28.412992477416992 83.24114990234375,-28.321502685546875 83.23729705810547,-28.228504180908203 83.23190307617188,-28.172082901000977 83.2298812866211,-28.021299362182617 83.2071533203125,-27.913063049316406 83.2000961303711,-27.84296226501465 83.19336700439453,-27.76662826538086 83.1988296508789,-27.67046546936035 83.20411682128906,-27.603302001953125 83.2081298828125,-27.51640510559082 83.20278930664062,-27.44464683532715 83.20829010009766,-27.354520797729492 83.2197494506836,-27.276744842529297 83.2175521850586,-27.204906463623047 83.23070526123047,-27.1240291595459 83.23306274414062,-27.053749084472656 83.24317169189453,-26.988027572631836 83.25484466552734,-26.93454360961914 83.2635726928711,-26.862899780273438 83.25835418701172,-26.824668884277344 83.2672119140625,-26.784603118896484 83.30513000488281,-26.7872314453125 83.3449478149414,-26.86507225036621 83.34102630615234,-26.961252212524414 83.3342056274414,-27.051280975341797 83.33192443847656,-27.16728973388672 83.32527160644531,-27.269407272338867 83.33380889892578,-27.362356185913086 83.34379577636719,-27.453813552856445 83.3507080078125,-27.523916244506836 83.35743713378906,-27.601383209228516 83.38871765136719,-27.69301986694336 83.37879943847656,-27.776914596557617 83.37952423095703,-27.85170555114746 83.37557983398438,-27.94019317626953 83.37481689453125,-27.995153427124023 83.37069702148438,-28.091154098510742 83.38070678710938,-28.19450569152832 83.41680908203125,-28.243257522583008 83.42422485351562,-28.15041160583496 83.43084716796875,-28.15256118774414 83.43514251708984,-28.275022506713867 83.43943786621094,-28.363109588623047 83.44374084472656,-28.440454483032227 83.45233154296875,-28.440454483032227 83.45662689208984,-28.43830680847168 83.45662689208984,-28.306310653686523 83.464599609375,-28.197219848632812 83.46693420410156,-28.149723052978516 83.46914672851562,-28.095001220703125 83.46998596191406,-28.050556182861328 83.47164916992188,-28.00444793701172 83.47470092773438,-27.961944580078125 83.47970581054688,-27.91388702392578 83.48165893554688,-27.86083221435547 83.48109436035156,-27.751113891601562 83.47776794433594,-27.581668853759766 83.46998596191406,-27.43282699584961 83.46661376953125,-27.28722381591797 83.45416259765625,-27.176666259765625 83.44999694824219,-26.982776641845703 83.4366455078125,-26.864444732666016 83.42747497558594,-26.80889129638672 83.42498779296875,-26.751667022705078 83.42109680175781,-26.340557098388672 83.38804626464844,-26.25777816772461 83.38388061523438,-26.199722290039062 83.379150390625,-26.099998474121094 83.36970520019531,-26.071666717529297 83.364990234375,-26.049724578857422 83.35887145996094,-26.024723052978516 83.35359191894531,-25.95861053466797 83.34332275390625,-25.84972381591797 83.33526611328125,-25.800277709960938 83.33055114746094,-25.775558471679688 83.32499694824219,-25.759166717529297 83.31944274902344,-25.68499755859375 83.3035888671875,-25.669998168945312 83.29887390136719,-25.658611297607422 83.29136657714844,-25.65589141845703 83.2909927368164,-25.655834197998047 83.28359985351562,-25.67194366455078 83.27748107910156,-25.703887939453125 83.27110290527344,-25.74388885498047 83.26443481445312,-25.82805633544922 83.25387573242188,-25.92388916015625 83.24026489257812,-25.944164276123047 83.23442077636719,-25.971942901611328 83.22248840332031,-25.988887786865234 83.21720886230469,-26.008892059326172 83.21388244628906,-26.038890838623047 83.20664978027344,-26.122222900390625 83.20193481445312,-26.304447174072266 83.19692993164062,-26.42361068725586 83.18609619140625,-26.46221923828125 83.18193054199219,-26.53778076171875 83.17221069335938,-26.573055267333984 83.16859436035156,-26.711387634277344 83.16249084472656,-26.755836486816406 83.15971374511719,-26.792499542236328 83.15664672851562,-26.86888885498047 83.14833068847656,-26.95610809326172 83.14360046386719,-27.087223052978516 83.14109802246094,-27.227497100830078 83.14193725585938,-27.375831604003906 83.13970947265625,-27.60916519165039 83.13442993164062,-27.756946563720703 83.13471984863281,-27.811111450195312 83.13749694824219,-27.86333465576172 83.13916015625,-27.91388702392578 83.13970947265625,-28.060279846191406 83.13888549804688,-28.161945343017578 83.14276123046875,-28.22083282470703 83.14553833007812,-28.276390075683594 83.14915466308594,-28.488330841064453 83.15887451171875,-28.59972381591797 83.16331481933594,-28.810001373291016 83.16943359375,-29.344444274902344 83.175537109375,-29.39638900756836 83.17442321777344,-29.553333282470703 83.17387390136719,-29.64444351196289 83.17192077636719,-29.79361343383789 83.17137145996094,-29.949443817138672 83.1724853515625,-30.047500610351562 83.17137145996094,-30.188610076904297 83.16720581054688,-30.374168395996094 83.16053771972656,-30.461109161376953 83.1563720703125,-30.54389190673828 83.15054321289062,-30.621665954589844 83.14414978027344,-30.695831298828125 83.13442993164062,-30.734996795654297 83.12776184082031,-30.769447326660156 83.12052917480469,-30.805835723876953 83.11442565917969,-30.840557098388672 83.11137390136719,-30.93000030517578 83.11053466796875,-30.972774505615234 83.11137390136719,-31.05722427368164 83.1099853515625,-31.132221221923828 83.10664367675781,-31.310279846191406 83.09553527832031,-31.389442443847656 83.08831787109375,-31.504722595214844 83.07916259765625,-31.58416748046875 83.07026672363281,-31.613887786865234 83.06553649902344,-31.641944885253906 83.05941772460938,-31.6602783203125 83.05693054199219,-31.80694580078125 83.05581665039062,-31.92916488647461 83.05165100097656,-32.021942138671875 83.05247497558594,-32.137779235839844 83.05941772460938,-32.301109313964844 83.07582092285156,-32.337501525878906 83.08055114746094,-32.39250183105469 83.08970642089844,-32.46305847167969 83.09832763671875,-32.51500701904297 83.10247802734375,-32.79389190673828 83.12052917480469,-32.85166931152344 83.12359619140625,-33.07417297363281 83.13859558105469,-33.228050231933594 83.14610290527344,-33.33416748046875 83.14942932128906,-33.387779235839844 83.15054321289062,-33.47471618652344 83.14694213867188,-33.483055114746094 83.1422119140625,-33.42583465576172 83.1361083984375,-33.32695007324219 83.13388061523438,-33.295005798339844 83.12942504882812,-33.29084014892578 83.12303161621094,-33.25361633300781 83.11831665039062,-33.21055603027344 83.1180419921875,-33.11860656738281 83.12136840820312,-33.01500701904297 83.11997985839844,-32.96721649169922 83.11775207519531,-32.882774353027344 83.11080932617188,-32.75666809082031 83.098876953125,-32.72471618652344 83.09443664550781,-32.648048400878906 83.08869171142578,-32.6431884765625 83.08769226074219,-32.631385803222656 83.08526611328125,-32.59471893310547 83.08055114746094,-32.55249786376953 83.06999206542969,-32.5191650390625 83.06330871582031,-32.49805450439453 83.05775451660156,-32.47138214111328 83.04693603515625,-32.473052978515625 83.04081726074219,-32.494720458984375 83.03610229492188,-32.52305603027344 83.03248596191406,-32.68444061279297 83.02053833007812,-32.75250244140625 83.01971435546875,-32.79695129394531 83.02110290527344,-32.846946716308594 83.02137756347656,-32.93250274658203 83.01914978027344,-32.95555877685547 83.01693725585938,-32.98444366455078 83.01165771484375,-32.98805236816406 83.00665283203125,-33.00917053222656 83.00166320800781,-33.03166961669922 83.00082397460938,-33.076393127441406 83.00221252441406,-33.123329162597656 83.00471496582031,-33.165550231933594 83.00498962402344,-33.21305847167969 83.00416564941406,-33.301109313964844 83.00138854980469,-33.55999755859375 82.988037109375,-33.605560302734375 82.9849853515625,-33.71888732910156 82.97442626953125,-33.741668701171875 82.97053527832031,-33.774169921875 82.96720886230469,-33.83472442626953 82.95887756347656,-33.93028259277344 82.94802856445312,-34.00750732421875 82.94136047363281,-34.13166809082031 82.93553161621094,-34.1763916015625 82.93386840820312,-34.317222595214844 82.93109130859375,-34.40638732910156 82.9305419921875,-34.53944396972656 82.93109130859375,-34.57417297363281 82.93193054199219,-34.663055419921875 82.93109130859375,-34.709999084472656 82.93193054199219,-34.76194763183594 82.93109130859375,-34.9566650390625 82.91804504394531,-35.07972717285156 82.91499328613281,-35.11888885498047 82.91276550292969,-35.1683349609375 82.91110229492188,-35.30332946777344 82.909423828125,-35.406944274902344 82.91110229492188,-35.50556182861328 82.91110229492188,-35.54528045654297 82.91026306152344,-35.57917022705078 82.90776062011719,-35.608612060546875 82.90664672851562,-35.61805725097656 82.90415954589844,-35.617774963378906 82.90109252929688,-35.59527587890625 82.89888000488281,-35.563331604003906 82.89776611328125,-35.518890380859375 82.89833068847656,-35.43250274658203 82.89694213867188,-35.35388946533203 82.89498901367188,-35.31416320800781 82.89276123046875,-35.29639434814453 82.88638305664062,-35.315834045410156 82.88442993164062,-35.39555358886719 82.87191772460938,-35.41944885253906 82.86581420898438,-35.426109313964844 82.85942077636719,-35.43055725097656 82.85359191894531,-35.424720764160156 82.84721374511719,-35.44554901123047 82.83720397949219,-35.43999481201172 82.82943725585938,-35.427223205566406 82.82470703125,-35.41221618652344 82.82221984863281,-35.4022216796875 82.818603515625,-35.401939392089844 82.81553649902344,-35.40666198730469 82.81442260742188,-35.413612365722656 82.81109619140625,-35.44444274902344 82.80470275878906,-35.49639129638672 82.79109191894531,-35.51721954345703 82.78414916992188,-35.54472351074219 82.77082824707031,-35.5513916015625 82.76443481445312,-35.55055236816406 82.75833129882812,-35.540283203125 82.7530517578125,-35.52527618408203 82.74775695800781,-35.48833465576172 82.7430419921875,-35.43499755859375 82.74136352539062,-35.38972473144531 82.74247741699219,-35.37555694580078 82.74609375,-35.383331298828125 82.75193786621094,-35.39805603027344 82.75721740722656,-35.41583251953125 82.76220703125,-35.423614501953125 82.76776123046875,-35.429168701171875 82.77415466308594,-35.429725646972656 82.7802734375,-35.411109924316406 82.78665161132812,-35.3780517578125 82.79525756835938,-35.287593841552734 82.81298828125,-35.27363586425781 82.76630401611328,-35.2453498840332 82.70024871826172,-35.273197174072266 82.66375732421875,-35.36191177368164 82.64156341552734,-35.471900939941406 82.62721252441406,-35.569671630859375 82.6142807006836,-35.65961837768555 82.6196517944336,-35.718116760253906 82.71351623535156,-35.655006408691406 82.76654052734375,-35.585975646972656 82.80268096923828,-35.62491989135742 82.870361328125,-35.68724822998047 82.89080047607422,-35.7452278137207 82.8897705078125,-35.82753372192383 82.8966064453125,-35.90394592285156 82.88349151611328,-35.99868392944336 82.86900329589844,-36.07808303833008 82.8620376586914,-36.18336486816406 82.8598861694336,-36.296142578125 82.87004852294922,-36.39535903930664 82.86477661132812,-36.48811340332031 82.89313507080078,-36.51934814453125 82.96839904785156,-36.458091735839844 82.99082946777344,-36.39710998535156 82.98724365234375,-36.32861328125 82.97286987304688,-36.263023376464844 82.97230529785156,-36.186885833740234 82.95940399169922,-36.10761642456055 82.9541244506836,-36.034461975097656 82.94737243652344,-36.06133270263672 83.00270080566406,-36.12372589111328 83.01702117919922,-36.18156051635742 83.0297622680664,-36.234771728515625 83.04705810546875,-36.29425811767578 83.04757690429688,-36.37022018432617 83.07730865478516,-36.3695182800293 83.14311981201172,-36.288658142089844 83.14395141601562,-36.20022201538086 83.14012908935547,-36.112125396728516 83.10416412353516,-36.02371597290039 83.09727478027344,-35.94746780395508 83.09508514404297,-35.869476318359375 83.11277770996094,-35.78053665161133 83.1563949584961,-35.68124008178711 83.1693115234375,-35.60636901855469 83.180908203125,-35.51350021362305 83.16326904296875,-35.40206527709961 83.16996002197266,-35.29670333862305 83.17976379394531,-35.199161529541016 83.1712646484375,-35.07413101196289 83.16558837890625,-34.958251953125 83.15999603271484,-34.82402038574219 83.1588363647461,-34.6913948059082 83.15003967285156,-34.599857330322266 83.1507797241211,-34.479305267333984 83.15432739257812,-34.38617706298828 83.16117095947266,-34.28999710083008 83.1679916381836,-34.112876892089844 83.18329620361328,-34.01960372924805 83.20391845703125,-33.88923263549805 83.2701416015625,-33.77778244018555 83.27835845947266,-33.662017822265625 83.26205444335938,-33.5692138671875 83.23828887939453,-33.450252532958984 83.23573303222656,-33.31782150268555 83.20856475830078,-33.1973991394043 83.19987487792969,-33.07374954223633 83.2079849243164,-32.94702911376953 83.21760559082031,-32.85700225830078 83.21988677978516,-32.707645416259766 83.20635223388672,-32.62078094482422 83.19794464111328,-32.491111755371094 83.19835662841797,-32.33378601074219 83.2168960571289,-32.20098114013672 83.22492980957031,-32.11682891845703 83.24868774414062,-32.026668548583984 83.26321411132812,-31.923057556152344 83.25160217285156,-31.786020278930664 83.22746276855469,-31.665775299072266 83.20193481445312,-31.540729522705078 83.19779205322266,-31.363933563232422 83.18248748779297,-31.220565795898438 83.17971801757812,-31.096961975097656 83.18324279785156,-30.99440574645996 83.21602630615234,-30.92693328857422 83.24911499023438,-30.82878875732422 83.2972412109375,-30.771976470947266 83.33195495605469,-30.76860237121582 83.36254119873047,-30.801769256591797 83.3995590209961,-30.868820190429688 83.40625762939453,-30.948074340820312 83.41307067871094,-31.036577224731445 83.41077423095703,-31.13272476196289 83.40701293945312,-31.207677841186523 83.38776397705078,-31.282501220703125 83.3807601928711,-31.370891571044922 83.38917541503906,-31.491296768188477 83.39939880371094,-31.610015869140625 83.42491149902344,-31.698291778564453 83.44404602050781,-31.797277450561523 83.460205078125,-31.914438247680664 83.48876953125,-32.00732421875 83.50487518310547,-32.100433349609375 83.49955749511719,-32.251739501953125 83.47332000732422,-32.315948486328125 83.46009826660156,-32.39999008178711 83.44705200195312,-32.48249053955078 83.43551635742188,-32.54204559326172 83.4299087524414,-32.62439727783203 83.4321517944336,-32.70681381225586 83.42827606201172,-32.81500244140625 83.43992614746094,-32.880592346191406 83.44049072265625,-32.964290618896484 83.45958709716797,-33.07406997680664 83.46512603759766,-33.12580108642578 83.47781372070312,-33.189605712890625 83.50285339355469,-33.2611198425293 83.52030944824219,-33.338897705078125 83.52251434326172,-33.43040466308594 83.52483367919922,-33.51433181762695 83.52249908447266,-33.566680908203125 83.47703552246094,-33.62660598754883 83.43623352050781,-33.68171691894531 83.41834259033203,-33.78412628173828 83.39933013916016,-33.845252990722656 83.38914489746094,-33.9519157409668 83.4007797241211,-34.040225982666016 83.4168472290039,-34.12540054321289 83.44054412841797,-34.2226676940918 83.47505950927734,-34.285125732421875 83.48324584960938,-34.39800262451172 83.48422241210938,-34.49567413330078 83.48047637939453,-34.56715393066406 83.50099182128906,-34.63676834106445 83.55363464355469,-34.67598342895508 83.59786987304688)))