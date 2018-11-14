package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static deals.util.Util.convertToDate;

/**
 * Created by psundriyal on 11/14/18.
 */

@Component
public class NoOfDaysFilter implements Filter {
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

    boolean doesMatch(PackageDeal packageDeal, String param) {
        int noOfDays = Integer.parseInt(param);
        Date inboundDate = convertToDate(packageDeal.getInboundDate());
        Date outboundDate = convertToDate(packageDeal.getOutboundDate());

        long diffInMillies = Math.abs((outboundDate.getTime() - inboundDate.getTime()));
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return Math.toIntExact(diff) == noOfDays;

    }
}
