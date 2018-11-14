package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 11/14/18.
 */

@Component
public class PackageFilter implements Filter {

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

    boolean doesMatch(PackageDeal packageDeal, String path) {

        if (path.equalsIgnoreCase("package") && packageDeal.isPackage()){
            return true;
        } else if (path.equalsIgnoreCase("flight") && !packageDeal.isPackage()){
            return true;
        }

        return false;
    }
}
