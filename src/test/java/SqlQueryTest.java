import deals.sql.SqlQueryGenerator;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by psundriyal on 4/8/18.
 */
public class SqlQueryTest {

    List<String> europe = Arrays.asList("CDG", "LHR", "VIE", "FCO", "MXP", "ZRH", "BRU","BCN","MAD","PRG", "AMS", "ATH");
    List<String> usa = Arrays.asList("LGA", "EWR", "SEA", "LAX", "SFO", "MIA", "BOS","DEN","ATL","LAS", "DFW", "PHL");
    List<String> hawai = Arrays.asList("HNL", "LIH", "OGG", "KOA", "ITO", "ANC");
    List<String> southamerica = Arrays.asList("GIG", "CTG", "LIM", "SJO", "SCL", "EZE", "TZA","GRU","BOG","PTY", "EZE", "SDQ");
    List<String> austrailia = Arrays.asList("SYD", "MEL", "ADL", "CBR");
    List<String> southEastAsia = Arrays.asList("BKK", "DPS", "MLE","KUL","SIN","CMB");




    @Test
    public void buildEuroQueryTest(){

        String query = SqlQueryGenerator.generateQuery("SEA", europe, 5,5);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildHawaiQueryTest(){

        String query = SqlQueryGenerator.generateQuery("ORD", hawai, 5,5);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildSimpleQueryTest(){

        List<String> destinations = Arrays.asList("BKK", "DPS", "MLE", "JMK", "MXP", "ZRH", "BRU","BCN","MAD","PRG", "AMS", "ATH");
        String query = SqlQueryGenerator.generateSimpleQuery("BOM", destinations, 8,3);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildSouthAmericaQueryTest(){

        String query = SqlQueryGenerator.generateQuery("ORD", southamerica, 5,4);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildgenerateAnyDayOfWeekQueryTest() {
        String query = SqlQueryGenerator.generateQuery("ORD", austrailia, 15, 5);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildgenerateAnyDayOfWeekQueryDOMTest() {
        String query = SqlQueryGenerator.generateSimpleQuery("ORD", usa,3,5);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildgenerate10QueryTest() {
        String query = SqlQueryGenerator.generateSuperSimpleQuery("ORD", Arrays.asList("CUN","LAS","LAX","MCO"));
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildAnyDayQueryTest() {
        String query  = SqlQueryGenerator.generateSimpleQuery("SEA", europe);
        System.out.println(query);
        Assert.notNull(query);
    }


    @Test
    public void buildMultiOriginQueryTest() {
        String query  = SqlQueryGenerator.generateMultiOriginOrQuery(Arrays.asList("ORD", "SEA"), europe, 5, 5);
        System.out.println(query);
        Assert.notNull(query);
    }

    @Test
    public void buildTopDest(){
        String query = SqlQueryGenerator.generateTopDest("SEA");
        System.out.println(query);
        Assert.notNull(query);
    }
}
