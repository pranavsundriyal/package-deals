package deals.filter;

import deals.sql.model.PackageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by psundriyal on 3/23/19.
 */

@Component
public class FilterManager {

    @Autowired
    MonthFilter monthFilter;

    @Autowired
    NoOfDaysHigherFilter noOfDaysHigherFilter;

    @Autowired
    NoOfDaysLowerFilter noOfDaysLowerFilter;

    @Autowired
    StartDayOfWeekFilter startDayOfWeekFilter;

    @Autowired
    EndDayofWeekFilter endDayofWeekFilter;

    @Autowired
    CarrierFilter carrierFilter;


    public List<PackageDeal> filter (List<PackageDeal> deals, String month, String startDay, String endDay,
                                     String noOfDaysLower, String noOfDaysHigher, String carrierCode) {

        if (month != null && !month.isEmpty()) {
            deals = monthFilter.filter(deals, month);
        }

        if (noOfDaysHigher != null && !noOfDaysHigher.isEmpty()) {
            deals = noOfDaysHigherFilter.filter(deals, noOfDaysHigher);
        }

        if (noOfDaysLower != null && !noOfDaysLower.isEmpty()) {
            deals = noOfDaysLowerFilter.filter(deals, noOfDaysLower);
        }

        if (startDay != null && !startDay.isEmpty()) {
            deals = startDayOfWeekFilter.filter(deals, startDay);
        }

        if (endDay != null && !endDay.isEmpty()) {
            deals = endDayofWeekFilter.filter(deals, endDay);
        }

        if (carrierCode !=null && !carrierCode.isEmpty()) {
            deals = carrierFilter.filter(deals, carrierCode);
        }
    return deals;
    }


}
