package deals.cache;

import deals.service.RedshiftConnector;
import deals.sql.SqlQueryGenerator;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static deals.Util.addValues;
import static deals.Util.sort;
import static deals.sql.SqlQueryGenerator.europe;

/**
 * Created by psundriyal on 6/17/18.
 */
@Configuration
public class CacheManager {

    private HashMap<String,List<PackageDeal>> packageDealMap;

    public  static  List<String> euro = Arrays.asList("CDG", "LHR", "ZRH", "BRU","BCN","MAD", "AMS");

    @Autowired
    RedshiftConnector redshiftConnector;

    @Autowired
    XmlUtil xmlUtil;

    public CacheManager() {
        packageDealMap = new HashMap<>();
    }

    public boolean cacheDeals(List<PackageDeal> packageDeals) {
        for (PackageDeal deal : packageDeals) {
            String key = generateKey(deal.getOrigin(), deal.getDestination());
            if (!packageDealMap.containsKey(key)){
                List<PackageDeal> dealList = new ArrayList<>();
                dealList.add(deal);
                packageDealMap.put(key,dealList);
            } else {
                List<PackageDeal> dealList = packageDealMap.get(key);
                dealList.add(deal);
                packageDealMap.put(key,dealList);
            }
        }

        return true;
    }

    public List<PackageDeal> getCachedDeals(String origin, String destination) {
        String key = generateKey(origin,destination);
        if (packageDealMap.containsKey(key)) {
            return packageDealMap.get(key);
        }

        return new ArrayList();
    }

    public List<PackageDeal> getCachedDeals(String origin) {

        List<PackageDeal> packageDeals = new ArrayList<>();
        Set<String> keys =  packageDealMap.keySet();
        for (String key : keys) {
            if(key.contains(origin)){
                packageDeals.addAll(packageDealMap.get(key));
            }
         }
        sort(packageDeals);
        return packageDeals;
    }

    public String generateKey(String origin, String destination) {
        return origin+"-"+destination;

    }

    @Scheduled(fixedRate = 86400000)
    public void clearCache() {
        packageDealMap = new HashMap<>();
        //String query = SqlQueryGenerator.generateQuery("ORD", euro, 5,5);
        //List<PackageDeal> deals = redshiftConnector.execute(query);
        List<PackageDeal> packageDeals = xmlUtil.read();
        packageDeals = addValues(packageDeals);
        cacheDeals(packageDeals);
    }
}
