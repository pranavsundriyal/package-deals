package deals.service;

import deals.sql.SqlQueryGenerator;
import deals.sql.model.Deals;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static deals.sql.SqlQueryGenerator.austrailia;
import static deals.sql.SqlQueryGenerator.europe;
import static deals.sql.SqlQueryGenerator.hawai;
import static deals.sql.SqlQueryGenerator.southamerica;
import static deals.sql.SqlQueryGenerator.usa;
import static deals.util.Util.addValues;

/**
 * Created by psundriyal on 8/4/18.
 */

@Service
public class CheapestPackageService {

    @Autowired
    GenericRedshiftConnector genericRedshiftConnector;

    @Autowired
    XmlUtil xmlWriter;

    public List<PackageDeal> execute(){
        List<PackageDeal> packageDeals = new ArrayList<>();
        List<String> world = new ArrayList<>();
        world.addAll(europe);
        world.addAll(hawai);
        world.addAll(usa);
        world.addAll(southamerica);
        world.addAll(austrailia);
        String query = SqlQueryGenerator.generateMultiOriginSuperSimpleQuery(Arrays.asList( "ORD"), Arrays.asList( "SEA"));
        List<List<Object>> objects = genericRedshiftConnector.execute(query);

        for(List<Object> objectList : objects) {
            packageDeals.add(new PackageDeal((Double) objectList.get(0),
                    null,
                    (Timestamp) objectList.get(3),
                    (Timestamp) objectList.get(4),
                    (String) objectList.get(5),
                    (String) objectList.get(1),
                    (String) objectList.get(2)));
        }

        packageDeals = addValues(packageDeals, false);
        Deals dealsList = new Deals();
        dealsList.setPackageDeals(packageDeals);
        xmlWriter.write(dealsList);

        return packageDeals;
    }

}
