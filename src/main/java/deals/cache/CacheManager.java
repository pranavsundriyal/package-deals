package deals.cache;

import deals.service.CheapFlightService;
import deals.service.CheapestPackageService;
import deals.service.GenericPackageDealService;
import deals.service.HalfPricePackageService;
import deals.service.PopularPackageDestinationService;
import deals.service.TopDestinationsService;
import deals.service.TopPackageNetDestinationService;
import deals.sort.Sort;
import deals.sql.model.Deals;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static deals.util.Util.sort;

/**
 * Created by psundriyal on 6/17/18.
 */
@Configuration
public class CacheManager {

    private HashMap<String,List<PackageDeal>> packageDealMap;

    @Autowired
    private HalfPricePackageService halfPricePackageService;

    @Autowired
    private TopPackageNetDestinationService topPackageNetDestinationService;

    @Autowired
    private TopDestinationsService topDestinationsService;

    @Autowired
    private PopularPackageDestinationService popularPackageDestinationService;

    @Autowired
    private GenericPackageDealService genericPackageDealService;

    @Autowired
    private CheapFlightService cheapFlightService;

    @Autowired
    private XmlUtil xmlUtil;

    @Autowired
    private Sort sort;

    @Value("${settings.readFromCache}")
    private boolean readFromCache;

    public CacheManager() {
        packageDealMap = new HashMap<>();
    }

    @Scheduled(fixedRate = 86400000)
    public void clearCache() {
        packageDealMap = new HashMap<>();
        List<PackageDeal> packageDeals = new ArrayList<>();

        if(readFromCache) {
            packageDeals = xmlUtil.read();

        } else {
            Set<String> topDestination = topPackageNetDestinationService.execute("ORD", 10);
            Set<String> popularDestination = popularPackageDestinationService.execute("ORD", 10);
            popularDestination.retainAll(topDestination);
            List<String> destinations = new ArrayList<>();
            destinations.addAll(popularDestination);
            packageDeals = genericPackageDealService.execute(Arrays.asList("ORD"), destinations);

            Set<String> popularDestinations = topDestinationsService.execute("ORD", 20);
            List<String> popularDestinationList = new ArrayList<>();
            popularDestinationList.addAll(popularDestinations);
            List cheapFlights = cheapFlightService.execute(Arrays.asList("ORD"), popularDestinationList,30);
            packageDeals.addAll(cheapFlights);

            packageDeals.addAll(halfPricePackageService.execute());

            xmlUtil.write(packageDeals);

        }

        cacheDeals(packageDeals);
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
            List<PackageDeal> packageDeals = packageDealMap.get(key);
            sort(packageDeals);
            return packageDeals;
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
        sort.sortByComparators(packageDeals);
        return packageDeals;
    }

    public String generateKey(String origin, String destination) {
        return origin+"-"+destination;

    }

}
