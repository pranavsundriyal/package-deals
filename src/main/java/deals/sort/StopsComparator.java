package deals.sort;

import deals.sql.model.PackageDeal;

import java.util.Comparator;

/**
 * Created by psundriyal on 8/12/18.
 */
public class StopsComparator implements Comparator<PackageDeal> {
    @Override
    public int compare(PackageDeal o1, PackageDeal o2) {
        return getNoStops(o1) - getNoStops(o2);
    }

    public int getNoStops(PackageDeal packageDeal) {
        String flights[]= packageDeal.getFlightNo().split(" ");
        return flights.length;
    }
}
