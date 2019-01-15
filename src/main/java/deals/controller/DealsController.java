package deals.controller;

import deals.cache.CacheManager;
import deals.filter.MonthFilter;
import deals.filter.NoOfDaysFilter;
import deals.filter.PackageFilter;
import deals.filter.YearFilter;
import deals.service.PackageDealService;
import deals.sort.PackagePriceComparator;
import deals.sort.Sort;
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
    MonthFilter monthFilter;

    @Autowired
    YearFilter yearFilter;

    @Autowired
    NoOfDaysFilter noOfDaysFilter;

    @Autowired
    PackageFilter packageFilter;

    @Autowired
    PackagePriceComparator packagePriceComparator;

    @Autowired
    Sort sort ;

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
                                            @RequestParam(value = "year", required = false) String year,
                                            @RequestParam(value = "noOfDays", required = false) String noOfDays,
                                            @RequestParam(value = "path", required = false) String path)
            throws Exception {

        List<PackageDeal> deals = new ArrayList();
        if (!dest.isEmpty()) {
            deals = cacheManager.getCachedDeals(origin,dest);
        } else {
            deals = cacheManager.getCachedDeals(origin);
        }

        //deals = clone(deals);

        if (month != null && !month.isEmpty()) {
            deals = monthFilter.filter(deals, month);
        }
        if (year != null && !year.isEmpty()) {
            deals = yearFilter.filter(deals, year);
        }

        if (noOfDays != null && !noOfDays.isEmpty()) {
            deals = noOfDaysFilter.filter(deals, noOfDays);
        }

        if (path != null && !path.isEmpty()) {
            deals = packageFilter.filter(deals, path);
        }
        sort.sortByComparators(deals);
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
