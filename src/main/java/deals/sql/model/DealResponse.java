package deals.sql.model;

import java.util.List;
import java.util.Map;

/**
 * Created by psundriyal on 5/16/19.
 */

public class DealResponse {
    List<PackageDeal> packageDeals;
    Map<String, Float> airlineDominanceMap;

    public DealResponse(List<PackageDeal> packageDeals, Map<String, Float> airlineDominanceMap) {
        this.packageDeals = packageDeals;
        this.airlineDominanceMap = airlineDominanceMap;
    }

    public List<PackageDeal> getPackageDeals() {
        return packageDeals;
    }

    public Map<String, Float> getAirlineDominanceMap() {
        return airlineDominanceMap;
    }

}
