package deals.controller;

import deals.cache.CacheManager;
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

    @RequestMapping(value = "/getRedshiftDeals")
    public List<PackageDeal> getDeals(@RequestParam(value = "origin", required = true) String origin) throws Exception {
        List<PackageDeal> deals = packageDealService.execute();
        cacheManager.cacheDeals(deals);
        return deals;
    }


    @RequestMapping(value = "/getCachedDeals")
    public List<PackageDeal> getCachedDeals(@RequestParam(value = "origin", required = true) String origin,
                                            @RequestParam(value = "dest", required = true) String dest) throws Exception {
        List<PackageDeal> deals = new ArrayList();
        if (!dest.isEmpty()) {
            deals = cacheManager.getCachedDeals(origin,dest);
        } else {
            deals = cacheManager.getCachedDeals(origin);
        }
        return deals;
    }

}
