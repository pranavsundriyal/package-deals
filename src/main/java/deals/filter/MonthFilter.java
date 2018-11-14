package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 11/14/18.
 */

@Component
public class MonthFilter implements Filter {

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

    boolean doesMatch(PackageDeal packageDeal, String month) {
        String[] splited = packageDeal.getOutboundDate().split("\\s+");

        String formattedDate = splited[0].toString();

        String[] dateTime = formattedDate.split("-");

        return month.equalsIgnoreCase(dateTime[1]);

    }
}
