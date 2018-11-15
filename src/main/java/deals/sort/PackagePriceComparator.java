package deals.sort;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by psundriyal on 7/9/18.
 */
@Component
public class PackagePriceComparator implements Comparator<PackageDeal> {
    @Override
    public int compare(PackageDeal o1, PackageDeal o2) {
        return (int) Math.round(o1.getPackageNetPrice())- (int) Math.round(o2.getPackageNetPrice());
    }
}
