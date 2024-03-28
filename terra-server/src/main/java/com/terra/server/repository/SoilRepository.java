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

    private final static String soilInAriaSql = "WITH point AS (" +
            " SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom )" +
            ", buffered_point AS (" +
            "    SELECT ST_Buffer(geom, ?) AS geom" +
            "    FROM point) " +
            "SELECT ST_AsText(s.geom) as polygon, snum as soilNumber, faosoil as soilType " +
            "FROM soil s " +
            "WHERE ST_Intersects(s.geom, (SELECT geom FROM buffered_point));";

    private final static String soilInAriaPolygonSql=
            "WITH point AS (  SELECT ST_SetSRID(ST_MakePoint(?,?), 4326) AS geom ), " +
            " buffered_point AS ( SELECT ST_Buffer(geom, ?) AS geom FROM point) " +
            " SELECT s.snum AS soilNumber, s.faosoil AS soilType, ST_AsText((ST_DumpRings((ST_Dump(s.geom)).geom)).geom) AS polygon " +
            " FROM soil s " +
            " JOIN buffered_point bp ON ST_Intersects(s.geom, bp.geom);";

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

    public SoilRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<SoilDTO> getSoilMultipolygon(final double latitude, final double longitude, final double zoom) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(soilInAriaSql)) {
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.setDouble(3, zoom);

            ResultSet soilRecords = statement.executeQuery();
            List<SoilDTO> soilDTOS = new ArrayList<>();
            while (soilRecords.next()) {
                SoilDTO current = new SoilDTO();
                String multipolygon = soilRecords.getObject("polygon") == null ? null : soilRecords.getString("polygon");
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
        try (PreparedStatement statement = connection.prepareStatement(soilInAriaPolygonSql)) {
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            statement.setDouble(3, zoom);

            ResultSet soilRecords = statement.executeQuery();
            List<SoilDTO> soilDTOS = new ArrayList<>();
            while (soilRecords.next()) {
                SoilDTO current = new SoilDTO();
                String multipolygon = soilRecords.getObject("polygon") == null ? null : soilRecords.getString("polygon");
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

    public List<SoilDTO> getTestSoil() {
        List<SoilDTO> soilDTOS = new ArrayList<>();
        SoilDTO current = new SoilDTO();
        current.setCoordinates(parsePolygons("MULTIPOLYGON(((22.72883415222168 44.205162048339844,22.707319259643555 44.19157028198242,22.691160202026367 44.169349670410156,22.681140899658203 44.137359619140625,22.677040100097656 44.100460052490234,22.715730667114258 44.0312614440918,22.78499984741211 43.975730895996094,22.830209732055664 43.90425109863281,22.825130462646484 43.848670959472656,22.81300926208496 43.82184982299805,22.7886905670166 43.79882049560547,22.7502498626709 43.78921127319336,22.679649353027344 43.79447937011719,22.4962100982666 43.84906005859375,22.456680297851562 43.84471893310547,22.372434616088867 43.80266571044922,22.372222900390625 43.80305480957031,22.370277404785156 43.80611038208008,22.367778778076172 43.80888748168945,22.365278244018555 43.8113899230957,22.3619441986084 43.81416702270508,22.359445571899414 43.81694412231445,22.36055564880371 43.820552825927734,22.363056182861328 43.823333740234375,22.365833282470703 43.82638931274414,22.369443893432617 43.8305549621582,22.372222900390625 43.83333206176758,22.37416648864746 43.836944580078125,22.376388549804688 43.840274810791016,22.379167556762695 43.84360885620117,22.381389617919922 43.84694290161133,22.38361167907715 43.850555419921875,22.385833740234375 43.853885650634766,22.387222290039062 43.8577766418457,22.38888931274414 43.861942291259766,22.39055633544922 43.8658332824707,22.392221450805664 43.869720458984375,22.393888473510742 43.87360763549805,22.39444351196289 43.877777099609375,22.39472198486328 43.88194274902344,22.39527702331543 43.886112213134766,22.39555549621582 43.89027786254883,22.39611053466797 43.894447326660156,22.3972225189209 43.89889144897461,22.398889541625977 43.90277862548828,22.401111602783203 43.90639114379883,22.4022216796875 43.91083526611328,22.40194320678711 43.91472244262695,22.399166107177734 43.9172248840332,22.397499084472656 43.92028045654297,22.395000457763672 43.923057556152344,22.393054962158203 43.92611312866211,22.392778396606445 43.92972183227539,22.395000457763672 43.93333435058594,22.398056030273438 43.9355583190918,22.40194320678711 43.9375,22.405555725097656 43.93916702270508,22.40916633605957 43.94111251831055,22.413055419921875 43.943058013916016,22.414443969726562 43.94694519042969,22.41499900817871 43.95111083984375,22.4152774810791 43.95528030395508,22.41583251953125 43.95916748046875,22.41611099243164 43.96333312988281,22.41666603088379 43.967498779296875,22.41694450378418 43.9716682434082,22.41666603088379 43.975555419921875,22.4163875579834 43.97916793823242,22.4152774810791 43.98250198364258,22.412776947021484 43.98527908325195,22.412500381469727 43.989166259765625,22.415555953979492 43.991390228271484,22.418888092041016 43.99388885498047,22.419721603393555 43.997501373291016,22.418054580688477 44.00055694580078,22.41611099243164 44.003334045410156,22.41694450378418 44.00694274902344,22.420276641845703 44.00944519042969,22.4244441986084 44.010833740234375,22.42860984802246 44.01194381713867,22.432777404785156 44.01333236694336,22.43805503845215 44.01361083984375,22.44222068786621 44.01499938964844,22.445554733276367 44.0172233581543,22.448331832885742 44.02027893066406,22.45194435119629 44.02194595336914,22.456111907958984 44.02333450317383,22.461944580078125 44.02305603027344,22.467500686645508 44.02166748046875,22.47361183166504 44.02083206176758,22.4777774810791 44.022220611572266,22.481111526489258 44.024723052978516,22.484722137451172 44.026390075683594,22.489444732666016 44.0272216796875,22.495834350585938 44.026390075683594,22.501388549804688 44.0261116027832,22.50722312927246 44.02583312988281,22.5130558013916 44.02555465698242,22.518333435058594 44.02583312988281,22.523056030273438 44.026390075683594,22.5272216796875 44.02777862548828,22.530832290649414 44.029720306396484,22.53416633605957 44.031944274902344,22.536943435668945 44.03499984741211,22.538888931274414 44.038333892822266,22.53999900817871 44.04194259643555,22.538055419921875 44.04499816894531,22.536943435668945 44.04833221435547,22.53444480895996 44.051109313964844,22.53416633605957 44.05472183227539,22.536943435668945 44.057777404785156,22.54111099243164 44.05888748168945,22.545276641845703 44.06027603149414,22.54888916015625 44.06222152709961,22.552776336669922 44.06388854980469,22.558610916137695 44.0636100769043,22.564722061157227 44.06277847290039,22.570556640625 44.0625,22.575834274291992 44.06277847290039,22.578889846801758 44.064998626708984,22.581666946411133 44.06805419921875,22.586389541625977 44.06888961791992,22.592222213745117 44.06861114501953,22.59749984741211 44.06861114501953,22.602500915527344 44.06888961791992,22.607778549194336 44.06916427612305,22.613611221313477 44.06888961791992,22.61833381652832 44.06972122192383,22.622499465942383 44.070831298828125,22.62416648864746 44.07500076293945,22.623889923095703 44.078609466552734,22.623611450195312 44.08250045776367,22.623332977294922 44.08610916137695,22.622222900390625 44.0897216796875,22.621944427490234 44.09333038330078,22.620834350585938 44.09666442871094,22.619722366333008 44.100276947021484,22.618610382080078 44.10361099243164,22.617778778076172 44.10749816894531,22.616666793823242 44.11083221435547,22.615556716918945 44.114166259765625,22.614723205566406 44.117496490478516,22.614999771118164 44.121665954589844,22.615556716918945 44.125831604003906,22.615833282470703 44.12999725341797,22.61638832092285 44.1341667175293,22.61750030517578 44.138614654541016,22.618610382080078 44.14305877685547,22.619722366333008 44.14778137207031,22.621389389038086 44.151668548583984,22.62472152709961 44.15388870239258,22.627777099609375 44.15639114379883,22.630001068115234 44.159725189208984,22.629722595214844 44.163612365722656,22.628055572509766 44.16666793823242,22.623889923095703 44.16861343383789,22.61916732788086 44.17000198364258,22.616666793823242 44.17277908325195,22.61833381652832 44.176666259765625,22.621389389038086 44.179168701171875,22.62472152709961 44.18138885498047,22.628889083862305 44.182777404785156,22.63361167907715 44.18361282348633,22.637500762939453 44.185279846191406,22.639999389648438 44.18833541870117,22.642221450805664 44.19166946411133,22.643333435058594 44.19611358642578,22.64527702331543 44.200279235839844,22.64638900756836 44.2047233581543,22.648056030273438 44.20861053466797,22.650278091430664 44.211944580078125,22.65333366394043 44.214447021484375,22.657222747802734 44.21611022949219,22.66083335876465 44.218055725097656,22.66499900817871 44.219444274902344,22.668888092041016 44.22111129760742,22.67249870300293 44.22305679321289,22.676387786865234 44.22472381591797,22.68027687072754 44.228057861328125,22.683887481689453 44.22138977050781,22.68638801574707 44.21889114379883,22.689722061157227 44.21638870239258,22.69305419921875 44.214168548583984,22.696943283081055 44.212223052978516,22.70194435119629 44.21111297607422,22.707500457763672 44.209999084472656,22.713056564331055 44.20861053466797,22.718610763549805 44.20750045776367,22.724166870117188 44.206390380859375,22.72883415222168 44.205162048339844)))"));
        current.setSoilNumber(3282);
        current.setSoilType("Vp68-3a");
        soilDTOS.add(current);
        return soilDTOS;
    }
}