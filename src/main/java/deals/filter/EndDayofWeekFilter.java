package deals.filter;

import deals.sql.model.PackageDeal;
import deals.util.Util;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by psundriyal on 3/13/19.
 */

@Component
public class EndDayofWeekFilter implements  Filter{

    @Override
    public List<PackageDeal> filter(List<PackageDeal> packageDeals, String param) {
        List<PackageDeal> filteredPackageDeals = new ArrayList<>();
        for (PackageDeal packageDeal : packageDeals) {
            if (doesMatch(packageDeal,Integer.parseInt(param))) {
                filteredPackageDeals.add(packageDeal);
            }
        }

        return filteredPackageDeals;
    }

    boolean doesMatch(PackageDeal packageDeal, int day) {
        String eDate = packageDeal.getInboundDate();
        if (eDate != null) {
            Date date = Util.convertToDate(eDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return dayOfWeek == day;
        }
        return false;
    }

    public List<PackageDeal> multiFilter(List<PackageDeal> packageDeals, List<String> paramList) {
        List<PackageDeal> filteredPackageDeals = new ArrayList<>();

        List<Integer> params = paramList.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        for (PackageDeal packageDeal : packageDeals) {
            if (doesMatch(packageDeal,params)) {
                filteredPackageDeals.add(packageDeal);
            }
        }

        return filteredPackageDeals;
    }

    boolean doesMatch(PackageDeal packageDeal, List<Integer> paramList) {
        String sDate = packageDeal.getInboundDate();
        if (sDate != null) {
            Date date = Util.convertToDate(sDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return paramList.contains(dayOfWeek);
        }
        return false;

    }
}
