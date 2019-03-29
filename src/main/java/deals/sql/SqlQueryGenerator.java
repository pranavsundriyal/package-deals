package deals.sql;

import deals.service.RedshiftConnector;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by psundriyal on 4/8/18.
 */

public class SqlQueryGenerator {

    private static final String UNION = "\nUNION";
    public static List<String> europe = Arrays.asList("CDG", "LHR", "VIE", "FCO", "MXP", "ZRH", "BRU","BCN","MAD","PRG", "AMS", "ATH");
    public static List<String> usa = Arrays.asList("LGA", "EWR", "SEA", "LAX", "SFO", "MIA", "BOS","DEN","ATL","LAS", "DFW", "PHL");
    public static List<String> hawai = Arrays.asList("HNL", "LIH", "OGG", "KOA", "ITO", "ANC");
    public static List<String> southamerica = Arrays.asList("GIG", "CTG", "LIM", "SJO", "SCL", "EZE", "TZA","GRU","BOG","PTY", "EZE", "SDQ");
    public static List<String> austrailia = Arrays.asList("SYD", "MEL", "ADL", "CBR");
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static  List<String> euro = Arrays.asList("CDG", "LHR", "ZRH", "BRU","BCN","MAD", "AMS");
    public static  List<String> MY_DESTINATIONS = Arrays.asList("DEL", "BOM", "BLR");


    public static List<String> topUsa = Arrays.asList("CUN","LAS", "LAX", "PHX", "PUJ", "MIA", "MBJ", "PVR", "SJO", "ZIH", "PTY", "SJU", "MCO", "DEN", "GCM", "HUX");



    public static String generateMultiOriginOrQuery(List<String> origins, List<String> destinations, int noOfDays, int dayOfWeek) {

        String query = generateQuery(origins.get(0), destinations, noOfDays, dayOfWeek);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateQuery(origins.get(i), destinations, noOfDays, dayOfWeek);
            query = query+ UNION + subQuery;
        }
        return query;
    }

        public static String generateQuery(String origin, List<String> destinations, int noOfDays, int dayOfWeek) {

        LocalDate searchDate = LocalDate.now().minusDays(2);


        String query = "(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                "from ods.air_search_results pack, ods.air_search_results pub\n" +
                "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '"+destinations.get(0)+"'  and\n" +
                " datediff(day, pack.outbound_date,pack.inbound_date) = "+noOfDays+" and date_part(dow, pack.outbound_date)= "+dayOfWeek+" and \n" +
                "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                "order by pack.total_price \n" +
                "limit 100)";

        for (int i = 1; i < destinations.size();i++) {
            String subQuery = "\n(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                                    "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                                    "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n"  +
                                    "from ods.air_search_results pack, ods.air_search_results pub\n" +
                                    "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                                    " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '"+destinations.get(i)+"'  and\n" +
                                    " datediff(day, pack.outbound_date,pack.inbound_date) = "+noOfDays+" and date_part(dow, pack.outbound_date)= "+dayOfWeek+" and \n" +
                                    "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                                    "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                                    "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                                    "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                                    "order by pack.total_price \n" +
                                    "limit 100)";

        query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateMultiOriginSimpleQuery(List<String> origins, List<String> destinations, int noOfDays, int dayOfWeek) {

        String query = generateQuery(origins.get(0), destinations, noOfDays, dayOfWeek);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateSimpleQuery(origins.get(i), destinations, noOfDays, dayOfWeek);
            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateSimpleQuery(String origin, List<String> destinations, int noOfDays, int dayOfWeek) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(2);


        String query = "(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                "from ods.air_search_results \n" +
                "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '"+origin+"' and inbound_airport like '"+destinations.get(0)+"'  and\n" +
                "datediff(day, outbound_date, inbound_date) = "+noOfDays+" and date_part(dow, outbound_date)= "+dayOfWeek+" and trip_type like 'roundtrip' and \n" +
                " fare_type like 'package net' \n" +
                "order by total_price \n" +
                "limit 400)";

        for (int i = 1; i < destinations.size();i++) {
            String subQuery = "\n(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                    "from ods.air_search_results" +"\n" +
                    "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '"+origin+"' and inbound_airport like '"+destinations.get(i)+"'  and\n" +
                    "datediff(day, outbound_date, inbound_date) = "+noOfDays+" and date_part(dow, outbound_date)= "+dayOfWeek+" and trip_type like 'roundtrip' and \n" +
                    "fare_type like 'package net' \n" +
                    "order by total_price \n" +
                    "limit 400)";

            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateMultiOriginAnyDayQuery(List<String> origins, List<String> destinations, int noOfDays) {

        String query = generateAnyDayOfWeekQuery(origins.get(0), destinations, noOfDays);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateAnyDayOfWeekQuery(origins.get(i), destinations,noOfDays);
            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateAnyDayOfWeekQuery(String origin, List<String> destinations, int noOfDays) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(3);


        String query = "(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                "from ods.air_search_results pack, ods.air_search_results pub\n" +
                "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(0) + "'  and\n" +
                " datediff(day, pack.outbound_date,pack.inbound_date) = " + noOfDays + " and \n" +
                "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                "order by pack.total_price \n" +
                "limit 100)";

        for (int i = 1; i < destinations.size(); i++) {
            String subQuery = "\n(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                    "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                    "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                    "from ods.air_search_results pack, ods.air_search_results pub\n" +
                    "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                    " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(i) + "'  and\n" +
                    " datediff(day, pack.outbound_date,pack.inbound_date) = " + noOfDays + " and \n" +
                    "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                    "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                    "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                    "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                    "order by pack.total_price \n" +
                    "limit 100)";

            query = query + UNION + subQuery;
        }
        return query;
    }

    public static String generateMultiOriginSimpleQuery(List<String> origins, List<String> destinations) {

        String query = generateSimpleQuery(origins.get(0), destinations);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateSimpleQuery(origins.get(i), destinations);
            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateSimpleQuery(String origin, List<String> destinations) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(3);


        String query = "(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                "from ods.air_search_results pack, ods.air_search_results pub\n" +
                "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(0) + "'  and\n" +
                "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                "order by pack.total_price \n" +
                "limit 100)";

        for (int i = 1; i < destinations.size(); i++) {
            String subQuery = "\n(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                    "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                    "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                    "from ods.air_search_results pack, ods.air_search_results pub\n" +
                    "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                    " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(i) + "'  and\n" +
                    "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                    "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                    "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                    "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                    "order by pack.total_price \n" +
                    "limit 100)";

            query = query + UNION + subQuery;
        }
        return query;
    }


    public static String generateMultiOriginSuperSimpleQuery(List<String> origins, List<String> destinations) {

        String query = generateSuperSimpleQuery(origins.get(0), destinations);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateSuperSimpleQuery(origins.get(i), destinations);
            query = query+ UNION + subQuery;
        }
        return query;
    }
    public static String generateSuperSimpleQuery(String origin, List<String> destinations) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(1);


        String query = "(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                "from ods.air_search_results \n" +
                "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '" + origin + "' and inbound_airport like '" + destinations.get(0) + "'  and\n" +
                "trip_type like 'roundtrip' and \n" +
                " fare_type like 'package net' \n" +
                "order by total_price \n" +
                "limit 200)";

        for (int i = 1; i < destinations.size(); i++) {
            String subQuery = "\n(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                    "from ods.air_search_results" + "\n" +
                    "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '" + origin + "' and inbound_airport like '" + destinations.get(i) + "'  and\n" +
                    "trip_type like 'roundtrip' and \n" +
                    "fare_type like 'package net' \n" +
                    "order by total_price \n" +
                    "limit 200)";

            query = query + UNION + subQuery;
        }
        return query;
    }

    public static String generateMultiOriginCheapQuery(List<String> origins, List<String> destinations, int limit) {

        String query = generateCheapQuery(origins.get(0), destinations, limit);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateCheapQuery(origins.get(i), destinations, limit);
            query = query+ UNION + subQuery;
        }
        return query;
    }
    public static String generateCheapQuery(String origin, List<String> destinations, int limit) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(1);


        String query = "(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                "from ods.air_search_results \n" +
                "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '" + origin + "' and inbound_airport like '" + destinations.get(0) + "'  and\n" +
                "trip_type like 'roundtrip' and fare_type not like 'package net' and total_price > 100 \n" +
                "order by total_price \n" +
                "limit "+limit+")";

        for (int i = 1; i < destinations.size(); i++) {
            String subQuery = "\n(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                    "from ods.air_search_results" + "\n" +
                    "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '" + origin + "' and inbound_airport like '" + destinations.get(i) + "'  and\n" +
                    "trip_type like 'roundtrip' and fare_type not like 'package net' and total_price > 100 \n" +
                    "order by total_price \n" +
                    "limit "+limit+")";

            query = query + UNION + subQuery;
        }
        return query;
    }

    public static String generateTopDestinationForPackageNets(String origin, int limit) {

        LocalDate searchDate = LocalDate.now().minusDays(2);

        String query = "select inbound_airport,count(*)\n" +
            "from ods.air_search_results \n" +
            "where search_date > '"+formatter.format(searchDate)+"' and trip_type like 'roundtrip' and \n" +
            "fare_type like 'package net' \n" +"and outbound_airport like '"+origin+"' group by inbound_airport\n" +
            "order by count(*) DESC\n" +
            "limit " + limit;

    return query;
    }



    public static String generatePopularPackageDestination(String origin, int number) {

        LocalDate searchDate = LocalDate.now().minusDays(2);

        String query = "select inbound_place, count(*)\n" +
                "from ods.air_search_summary\n" +
                "where search_date > '"+formatter.format(searchDate)+"' and trip_type like 'roundtrip' and " +
                "is_package = true and outbound_place like '"+origin+"'\n" +
                "group by inbound_place\n" +
                "order by count(*) desc\n" +
                "limit " + number;

        return query;
    }

    public static String generatePopularDestination(String origin, int number) {

        LocalDate searchDate = LocalDate.now().minusDays(2);

        String query = "select inbound_place, count(*)\n" +
                "from ods.air_search_summary\n" +
                "where search_date > '"+formatter.format(searchDate)+"' and trip_type like 'roundtrip' and " +
                "is_package = false and outbound_place like '"+origin+"'\n" +
                "group by inbound_place\n" +
                "order by count(*) desc\n" +
                "limit " + number;

        return query;
    }


    public static String generateMultiOriginComplexQuery(List<String> origins, List<String> destinations,
                                                         int noOfDaysMin, int noOfDaysMax,
                                                         int dayOfWeekMin, int dayOfWeekMax,
                                                         int limit) {

        String query = generateComplexQuery(origins.get(0), destinations, noOfDaysMin, noOfDaysMax, dayOfWeekMin, dayOfWeekMax, limit);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateComplexQuery(origins.get(i), destinations, noOfDaysMin, noOfDaysMax, dayOfWeekMin, dayOfWeekMax, limit);
            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateComplexQuery(String origin, List<String> destinations,
                                              int noOfDaysMin, int noOfDaysMax,
                                              int dayOfWeekMin, int dayOfWeekMax,
                                              int limit) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(2);


        String query = "(select distinct total_price, outbound_airport, inbound_airport, outbound_departure_time, inbound_arrival_time, marketing_flights\n" +
                "from ods.air_search_results \n" +
                "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '"+origin+"' and inbound_airport like '"+destinations.get(0)+"'  and\n" +
                +noOfDaysMin+  " < datediff(day, outbound_date, inbound_date) and datediff(day, outbound_date, inbound_date) < "+noOfDaysMax + "and "
                + dayOfWeekMin + " < date_part(dow, outbound_date) and  date_part(dow, outbound_date) < "+dayOfWeekMax+" " + "and " +
                "trip_type like 'roundtrip' and fare_type like 'package net' \n" +
                "order by total_price \n" +
                "limit " + limit + ")";

        for (int i = 1; i < destinations.size();i++) {
            String subQuery = "\n(select distinct total_price, outbound_airport,inbound_airport,outbound_departure_time, inbound_arrival_time,marketing_flights\n" +
                    "from ods.air_search_results" +"\n" +
                    "where search_date > '" + formatter.format(searchDate) + "' and outbound_airport like '"+origin+"' and inbound_airport like '"+destinations.get(i)+"'  and\n" +
                    +noOfDaysMin+  " < datediff(day, outbound_date, inbound_date) and datediff(day, outbound_date, inbound_date) < "+noOfDaysMax + "and "
                    + dayOfWeekMin + " < date_part(dow, outbound_date) and  date_part(dow, outbound_date) < "+dayOfWeekMax+" " + "and " +
                    "trip_type like 'roundtrip' and fare_type like 'package net' \n" +
                    "order by total_price \n" +
                    "limit " + limit + ")";

            query = query+ UNION + subQuery;
        }
        return query;
    }



    public static String generateMultiOriginOrHalfPricePackageQuery(List<String> origins, List<String> destinations,
                                                                    int noOfDaysMin, int noOfDaysMax,
                                                                    int dayOfWeekMin, int dayOfWeekMax,
                                                                    int limit) {

        String query = generateHalfPricePackageQuery(origins.get(0), destinations, noOfDaysMin, noOfDaysMax, dayOfWeekMin, dayOfWeekMax, limit);

        for (int i = 1; i < origins.size();i++) {
            String subQuery = generateHalfPricePackageQuery(origins.get(i), destinations, noOfDaysMin, noOfDaysMax, dayOfWeekMin, dayOfWeekMax, limit);
            query = query+ UNION + subQuery;
        }
        return query;
    }

    public static String generateHalfPricePackageQuery(String origin, List<String> destinations,
                                                       int noOfDaysMin, int noOfDaysMax,
                                                       int dayOfWeekMin, int dayOfWeekMax,
                                                       int limit) {

        LocalDate searchDate = LocalDate.now().minusDays(2);


        String query = "(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                "from ods.air_search_results pack, ods.air_search_results pub\n" +
                "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(0) + "'  and \n" +
                +noOfDaysMin + " < datediff(day, pack.outbound_date, pack.inbound_date) and datediff(day, pack.outbound_date, pack.inbound_date) < " + noOfDaysMax + "and "
                + dayOfWeekMin + " < date_part(dow, pack.outbound_date) and  date_part(dow, pack.outbound_date) < " + dayOfWeekMax + " " + "and " +
                "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                "order by pack.total_price \n" +
                "limit " + limit + ")";

        for (int i = 1; i < destinations.size(); i++) {
            String subQuery = "\n(select distinct pack.total_price as pack_price, pub.total_price as sa_price," +
                    "pub.outbound_airport,pub.inbound_airport,pub.outbound_departure_time, pub.inbound_arrival_time,\n" +
                    "pack.fare_type,pub.fare_type, pub.marketing_flights, pack.cabin_class, pub.cabin_class\n" +
                    "from ods.air_search_results pack, ods.air_search_results pub\n" +
                    "where pack.search_date > '" + formatter.format(searchDate) + "' and pub.search_date = pack.search_date  and \n" +
                    " pack.outbound_airport like '" + origin + "' and pack.inbound_airport like '" + destinations.get(i) + "'  and \n" +
                    +noOfDaysMin + " < datediff(day, pack.outbound_date, pack.inbound_date) and datediff(day, pack.outbound_date, pack.inbound_date) < " + noOfDaysMax + "and "
                    + dayOfWeekMin + " < date_part(dow, pack.outbound_date) and  date_part(dow, pack.outbound_date) < " + dayOfWeekMax + " " + "and " +
                    "pack.trip_type like 'roundtrip' and pub.outbound_airport = pack.outbound_airport and pub.inbound_airport = pack.inbound_airport and pack.trip_type = pub.trip_type and \n" +
                    "pack.marketing_flights = pub.marketing_flights and pack.departure_times = pub.departure_times and pack.arrival_times = pub.arrival_times and\n" +
                    "pack.fare_type like 'package net' and pub.fare_type not like 'package net' and pub.total_price > pack.total_price and\n" +
                    "pub.total_price - pack.total_price > pub.total_price/2 \n" +
                    "order by pack.total_price \n" +
                    "limit " + limit + ")";

            query = query + UNION + subQuery;
        }
        return query;
    }
}
