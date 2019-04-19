package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 4/18/19.
 */

@Component
public class CarrierFilter implements Filter {
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

    private  boolean doesMatch(PackageDeal packageDeal, String carrier) {
        if (carrier != null && packageDeal.getFlightNo() != null) {
            return packageDeal.getFlightNo().contains(carrier);
        }
        return false;
    }
}
