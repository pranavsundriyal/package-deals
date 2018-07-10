package deals.sort;

import deals.sql.model.PackageDeal;

import java.util.Comparator;

/**
 * Created by psundriyal on 7/9/18.
 */
public class SortBySavings implements Comparator<PackageDeal> {
    @Override
    public int compare(PackageDeal o1, PackageDeal o2) {
        return (int) Math.round(o1.getSavings())- (int) Math.round(o2.getSavings());
    }
}
