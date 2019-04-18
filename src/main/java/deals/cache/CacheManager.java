package deals.cache;

import deals.service.CheapFlightService;
import deals.service.CheapestPackageService;
import deals.service.GenericPackageDealService;
import deals.service.HalfPricePackageService;
import deals.service.PopularPackageDestinationService;
import deals.service.TopDestinationsService;
import deals.service.TopPackageNetDestinationService;
import deals.sort.SortManager;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static deals.sql.SqlQueryGenerator.MY_DESTINATIONS;
import static deals.sql.SqlQueryGenerator.euro;

/**
 * Created by psundriyal on 6/17/18.
 */
@Configuration
public class CacheManager {

    private Logger log = Logger.getLogger(CacheManager.class.getName());

    private ConcurrentHashMap<String,List<PackageDeal>> packageDealMap;

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
    private CheapestPackageService cheapestPackageService;

    @Autowired
    private CheapFlightService cheapFlightService;

    @Autowired
    private XmlUtil xmlUtil;

    @Autowired
    private SortManager sort;

    @Value("${settings.readFromCache}")
    private boolean readFromCache;

    public CacheManager() {
        packageDealMap = new ConcurrentHashMap();
    }

    @Scheduled(fixedRate = 2*86400000)
    public void clearCache() {
        List<PackageDeal> packageDeals = new ArrayList<>();

        if(readFromCache) {
            packageDeals = xmlUtil.read();

        } else {

            log.info("Getting top package net destination");
            //Set<String> destinationsWithPackageNetOffers = topPackageNetDestinationService.execute("ORD", 25);
            //destinationsWithPackageNetOffers.stream().forEach(destination -> log.info(destination));

            log.info("Getting top popular package  destination");
            List<String> popularPackageDestinations = popularPackageDestinationService.execute("ORD", 5);
            popularPackageDestinations.stream().forEach(destination -> log.info(destination));

            //popularPackageDestinations.retainAll(topPackageNetDestinations);

            log.info("Getting most popular standalone destination");
            Set<String> popularDestinations = topDestinationsService.execute("ORD", 25);
            List<String> popularDestinationList = new ArrayList<>();
            popularDestinationList.addAll(popularDestinations);
            popularDestinations.stream().forEach(destination -> log.info(destination));

            log.info("Getting generic package deals");
            popularPackageDestinations.addAll(euro);
            packageDeals.addAll(cheapestPackageService.execute(Arrays.asList("ORD","SEA"), popularPackageDestinations, 100));

            popularDestinationList.addAll(MY_DESTINATIONS);

            log.info("Getting generic cheap flight deals");
            packageDeals.addAll(cheapFlightService.execute(Arrays.asList("ORD","SEA"), popularDestinationList,100));

            log.info("Getting generic half price package deals");
            //packageDeals.addAll(halfPricePackageService.execute());

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
            //sort(packageDeals);
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
        //sort.sortByComparators(packageDeals);
        return packageDeals;
    }

    public String generateKey(String origin, String destination) {
        return origin+"-"+destination;

    }

    public ConcurrentHashMap<String, List<PackageDeal>> getPackageDealMap() {
        return packageDealMap;
    }
}
