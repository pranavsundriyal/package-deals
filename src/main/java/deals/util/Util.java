package deals.util;

import deals.sort.PackagePriceComparator;
import deals.sql.model.PackageDeal;
import org.apache.http.client.utils.URIBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by psundriyal on 7/9/18.
 */
public  class Util {

    public static String endpoint = "https://www.expedia.com/Hotel-Search?packageType=fh&searchProduct=hotel";
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
            URIBuilder uri = new URIBuilder(endpoint);
            uri.addParameter("origin", packageDeal.getOrigin());
            uri.addParameter("destination", packageDeal.getDestination());
            uri.addParameter("startDate", formatDate(packageDeal.getOutboundDate()));
            uri.addParameter("endDate", formatDate(packageDeal.getInboundDate()));
            uri.addParameter("adults","1");
            return uri.toString();
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

}
