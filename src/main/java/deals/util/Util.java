package deals.util;

import deals.sort.PackagePriceComparator;
import deals.sql.model.PackageDeal;
import org.apache.http.client.utils.URIBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by psundriyal on 7/9/18.
 */
public  class Util {

    public static String package_endpoint = "https://www.expedia.com/Hotel-Search?packageType=fh&searchProduct=hotel";
    public static String flight_endpoint = "https://www.expedia.com/Flights-Search?trip=roundtrip";
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static List<PackageDeal> addValues(List<PackageDeal> packageDeals, boolean calculateSavings) {
        for(PackageDeal packageDeal : packageDeals) {
            if (calculateSavings) {
                packageDeal.setSavings(calculateSavings(packageDeal));
            }
            packageDeal.setOutboundDate(truncateDate(packageDeal.getOutboundDateTime().toString()));
            packageDeal.setInboundDate(truncateDate(packageDeal.getInboundDateTime().toString()));
            packageDeal.setUrl(Util.generateUrl(packageDeal));
        }
        return packageDeals;
    }


    public static String generateUrl(PackageDeal packageDeal) {
        try {
            String url;
            if (packageDeal.isPackage()) {
                URIBuilder uri = new URIBuilder(package_endpoint);
                uri.addParameter("origin", packageDeal.getOrigin());
                uri.addParameter("destination", packageDeal.getDestination());
                uri.addParameter("startDate", formatDate(packageDeal.getOutboundDate()));
                uri.addParameter("endDate", formatDate(packageDeal.getInboundDate()));
                //uri.addParameter("adults","1");
                url = uri.toString();
            } else {
                StringBuilder sb = new StringBuilder(flight_endpoint);
                sb.append("&leg1=from:" + packageDeal.getOrigin() + ",to:" + packageDeal.getDestination()
                        + ",departure:" + formatDateForFLight(packageDeal.getOutboundDate()));
                sb.append("&leg2=from:" + packageDeal.getDestination() + ",to:" + packageDeal.getOrigin()
                        + ",departure:"+ formatDateForFLight(packageDeal.getInboundDate()));
                //sb.append("&passengers=children:0,adults:1,seniors:0,infantinlap:Y");
                sb.append("&mode=search");
                sb.append("&options=sortby:price");
                url = sb.toString();
/*
                uri = new URIBuilder(flight_endpoint);
                uri.addParameter("leg1", "from:" + packageDeal.getOrigin() + ",to:" + packageDeal.getDestination()
                        + ",departure:"+ formatDateForFLight(packageDeal.getOutboundDate()));
                uri.addParameter("leg2", "from:" + packageDeal.getDestination() + ",to:" + packageDeal.getOrigin()
                        + ",departure:"+ formatDateForFLight(packageDeal.getOutboundDate()));
                uri.addParameter("adults","1");
                uri.addParameter("mode","search");
                uri.addParameter("options","sortby:price");
            */
            }


            return url;
        } catch (Exception e){
            System.out.print(e);
        }
        return null;
    }

    public static String truncateDate(String date) {
        return date.substring(0,16);
    }

    public static String formatDate(String date) {

        String[] splited = date.split("\\s+");

        String formattedDate = splited[0].toString();

        return formattedDate;
    }

    public static String formatDateForFLight(String date) {

        String[] splited = date.split("\\s+");

        String formattedDate = splited[0].toString();

        String[] dateTime = formattedDate.split("-");

        String day = dateTime[2];

        String month = dateTime[1];

        String year = dateTime[0];

        return month+"/"+day+"/"+year+"TANYT";
    }

    public static Double calculateSavings(PackageDeal packageDeal) {
        return (packageDeal.getStandalonePrice() - packageDeal.getPackageNetPrice())*100/packageDeal.getStandalonePrice();
    }

    public static List<PackageDeal> sort(List<PackageDeal> packageDeals) {
        //Collections.sort(packageDeals, new SortBySavings());
        Collections.sort(packageDeals, new PackagePriceComparator());
        return packageDeals;
    }

    public static Date convertToDate(String date) {
        Date d = null;
        try {
            d = dateFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static int calculateDays(PackageDeal packageDeal) {
        Date inboundDate = convertToDate(packageDeal.getInboundDate());
        Date outboundDate = convertToDate(packageDeal.getOutboundDate());

        long diffInMillies = Math.abs((outboundDate.getTime() - inboundDate.getTime()));
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return Math.toIntExact(diff);
    }

}
