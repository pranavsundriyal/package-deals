package deals.controller;

import deals.cache.CacheManager;
import deals.filter.FilterManager;
import deals.service.PackageDealService;
import deals.sort.SortManager;
import deals.sql.model.PackageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static deals.util.Util.calculateDays;

/**
 * Created by psundriyal on 2/19/18.
 */

@RestController
@Component
public class DealsController {

    @Autowired
    PackageDealService packageDealService;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    FilterManager filterManager;

    @Autowired
    SortManager sortManager ;

    @RequestMapping(value = "/getRedshiftDeals")
    public List<PackageDeal> getDeals(@RequestParam(value = "origin", required = true) String origin) throws Exception {
        List<PackageDeal> deals = packageDealService.execute();
        cacheManager.cacheDeals(deals);
        return deals;
    }


    @RequestMapping(value = "/getCachedDeals")
    public List<PackageDeal> getCachedDeals(@RequestParam(value = "origin", required = true) String origin,
                                            @RequestParam(value = "dest", required = false) String dest,
                                            @RequestParam(value = "month", required = false) String month,
                                            @RequestParam(value = "noOfDays", required = false) String noOfDays,
                                            @RequestParam(value = "startDayOfWeek", required = false) String startDay,
                                            @RequestParam(value = "endDayOfWeek", required = false) String endDay,
                                            @RequestParam(value = "sort", required = false) String sortBy) throws Exception {

        List<PackageDeal> deals ;
        if (dest != null && !dest.isEmpty()) {
            deals = cacheManager.getCachedDeals(origin,dest);
        } else {
            deals = cacheManager.getCachedDeals(origin);
        }

        deals.stream().forEach(deal -> deal.setNoOfDays(calculateDays(deal)));

        deals = filterManager.filter(deals, month, startDay, endDay, noOfDays);

        sortManager.sortByComparators(deals, sortBy);

        return deals;
    }

    @RequestMapping(value = "/getDealSummary")
    public Map<String,Double> getDealSummary(@RequestParam(value = "origin", required = true) String origin) {

        Map<String,Double> summary = new HashMap<>();
        for (Map.Entry<String,List<PackageDeal>> entry : cacheManager.getPackageDealMap().entrySet()){
            String [] strings = entry.getKey().split("-");
            if (strings[0].equalsIgnoreCase(origin)) {
                List<PackageDeal> packageDeals = entry.getValue();
                //packageDeals.sort(packagePriceComparator);
                summary.put(strings[1], entry.getValue().get(0).getPackageNetPrice());
            }
        }

        return summary;

    }

    public List<PackageDeal> clone(List<PackageDeal> packageDeals){
        List<PackageDeal> clonedPackageDeals = new ArrayList<>();

        for (PackageDeal packageDeal : packageDeals) {
            PackageDeal deal = new PackageDeal(packageDeal.getPackageNetPrice(),
                    packageDeal.getStandalonePrice(),
                    packageDeal.getOutboundDateTime(),
                    packageDeal.getInboundDateTime(),
                    packageDeal.getFlightNo(),
                    packageDeal.getOrigin(),
                    packageDeal.getDestination(),
                    packageDeal.isPackage());

            deal.setOutboundDate(packageDeal.getOutboundDate());
            deal.setInboundDate(packageDeal.getInboundDate());
            deal.setUrl(packageDeal.getUrl());
            deal.setSavings(packageDeal.getSavings());

            clonedPackageDeals.add(deal);
        }

        return clonedPackageDeals;
    }


}
