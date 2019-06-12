package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 5/16/19.
 */

@Component
public class NoOfDaysLowerFilter implements Filter {

    @Override
    public List<PackageDeal> filter(List<PackageDeal> packageDeals, String param) {
        List<PackageDeal> filteredPackageDeals = new ArrayList<>();
        for (PackageDeal packageDeal : packageDeals) {
            if (doesMatch(packageDeal,param)) {
                filteredPackageDeals.add(packageDeal);
            }
        }

        return filteredPackageDeals;
    }

    boolean doesMatch(PackageDeal packageDeal, String param) {
        int noOfDaysLower = Integer.parseInt(param);
        return noOfDaysLower > packageDeal.getNoOfDays() ;
    }
}
