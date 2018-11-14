package deals.filter;

import deals.sql.model.PackageDeal;

import java.util.List;

/**
 * Created by psundriyal on 8/12/18.
 */
public interface Filter {

    List<PackageDeal> filter(List<PackageDeal> packageDeals, String param);

}
