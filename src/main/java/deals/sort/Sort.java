package deals.sort;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by psundriyal on 8/12/18.
 */
@Component
public class Sort {

    List<Comparator> comparators = new ArrayList<>();

    public Sort() {
        comparators.add(new PackagePriceComparator());
        //sorts.add(new SortBySavings());
        comparators.add(new StopsComparator());
    }

    public List<PackageDeal> sortByComparators(List<PackageDeal> packageDeals) {
        comparators.forEach(comparator -> Collections.sort(packageDeals, comparator));
        return packageDeals;
    }
}
