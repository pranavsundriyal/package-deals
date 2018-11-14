package deals.service;

import deals.sql.SqlQueryGenerator;
import deals.sql.model.PackageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static deals.util.Util.addValues;

/**
 * Created by psundriyal on 11/12/18.
 */

@Service
public class GenericPackageDealService {

    @Autowired
    GenericRedshiftConnector genericRedshiftConnector;

    public List<PackageDeal> execute(List origin, List destinations) {
        List<PackageDeal> packageDeals = new ArrayList<>();
        String query = SqlQueryGenerator.generateMultiOriginSimpleQuery(origin, destinations);
        List<List<Object>> objects = genericRedshiftConnector.execute(query);
        for(List<Object> objectList : objects) {
            packageDeals.add(new PackageDeal((Double) objectList.get(0),
                    (Double) objectList.get(1),
                    (Timestamp) objectList.get(4),
                    (Timestamp) objectList.get(5),
                    (String) objectList.get(8),
                    (String) objectList.get(2),
                    (String) objectList.get(3), true));
        }
        packageDeals = addValues(packageDeals, true);

        return packageDeals;
    }
}
