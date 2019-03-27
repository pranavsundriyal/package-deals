package deals.sort;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by psundriyal on 8/12/18.
 */
@Component
public class SortManager {

    Map<String, Comparator> comparatorMap = new HashMap();

    public SortManager() {
        comparatorMap.put("price", new PackagePriceComparator());
        comparatorMap.put("stops", new StopsComparator());
        comparatorMap.put("date", new DateComparator());
    }

    public List<PackageDeal> sortByComparators(List<PackageDeal> packageDeals, String comparatorType) {

        Comparator comparator = comparatorMap.get(comparatorType);
        if (comparator != null) {
            packageDeals.sort(comparator);
        }
        return packageDeals;
    }
}
