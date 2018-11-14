package deals.controller;

import deals.cache.CacheManager;
import deals.filter.MonthFilter;
import deals.filter.NoOfDaysFilter;
import deals.filter.PackageFilter;
import deals.filter.YearFilter;
import deals.service.PackageDealService;
import deals.sql.model.PackageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        return deals;
    }

}
