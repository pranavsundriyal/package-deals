package deals.sort;

import deals.sql.model.PackageDeal;
import deals.util.Util;

import java.util.Comparator;

/**
 * Created by psundriyal on 3/23/19.
 */
public class DateComparator implements Comparator<PackageDeal> {
    @Override
    public int compare(PackageDeal o1, PackageDeal o2) {
        return Util.convertToDate(o1.getOutboundDate()).compareTo(Util.convertToDate(o2.getOutboundDate()));
    }
}
