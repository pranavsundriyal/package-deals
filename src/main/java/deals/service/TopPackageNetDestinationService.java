package deals.service;

import deals.sql.SqlQueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by psundriyal on 6/17/18.
 */

@Component
public class TopPackageNetDestinationService {

    @Autowired
    GenericRedshiftConnector genericRedshiftConnector;

    private Logger log = Logger.getLogger(TopPackageNetDestinationService.class.getName());

    public Set<String> execute(String origin, int limit) {

        Set<String> destinations = new HashSet<>();
        List<List<Object>> objects = genericRedshiftConnector.execute(SqlQueryGenerator.
                generateTopDestinationForPackageNets(origin, limit));
        for(List<Object> objectList : objects) {
            destinations.add((String) objectList.get(0));
        }
        return destinations;
    }
}
